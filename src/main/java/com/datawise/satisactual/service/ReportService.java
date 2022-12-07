package com.datawise.satisactual.service;

import com.datawise.satisactual.mapper.StoredProcedureMapper;
import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.model.ReportNoteDetails;
import com.datawise.satisactual.model.ReportNoteRequest;
import com.datawise.satisactual.model.ReportRequest;
import com.datawise.satisactual.repository.StoredProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;

    @Autowired
    JdbcTemplate template;

    public List<List<Map<String, Object>>> findReports(ReportRequest request) {
        return executeProcedure(
                "call sp_get_survey_report(" +
                        request.getCampaignId() + "," +
                        request.getWaveId() + ",'" +
                        request.getDatStart() + "','" +
                        request.getDatEnd() + "','" +
                        request.getIncludeCover() + "','" +
                        request.getResponseSum() + "','" +
                        request.getNotQualifiedSum() + "','" +
                        request.getOutcomeSum() + "','" +
                        request.getIncludeNotSelected() + "','" +
                        request.getIncludeRatingDetail() + "','" +
                        request.getIncludeSampleSurvey() + "','" +
                        request.getShowRatingGroups() + "','" +
                        request.getLowRatingBelow() + "','" +
                        request.getHighRatingAbove() + "','" +
                        request.getTxtCurrUser()    + "'" +
                ");"
        );
    }

    private List<List<Map<String, Object>>> executeProcedure(final String sql) {
        return template.execute((CallableStatementCreator) con -> con.prepareCall(sql), cs -> {
            boolean resultsAvailable = cs.execute();
            List<List<Map<String, Object>>> list = new ArrayList<>();
            while (resultsAvailable) {
                ResultSet resultSet = cs.getResultSet();
                List<Map<String, Object>> subList = new ArrayList<>();
                while (resultSet.next()) {
                    ResultSetMetaData meta = resultSet.getMetaData();
                    int colCount = meta.getColumnCount();
                    Map<String, Object> map = new LinkedHashMap<>();
                    for (int i = 1; i <= colCount; i++) {
                        String name = meta.getColumnLabel(i);
                        map.put(name, resultSet.getString(i));
                    }
                    subList.add(map);
                }
                list.add(subList);
                resultsAvailable = cs.getMoreResults();
            }
            return list;
        });
    }

    public List<ReportNoteDetails> findReportNoteDetails(CommonPayload payload){
        return StoredProcedureMapper.mapReportNote(storedProcedureRepository.findReportsNotes(payload.getPinIdCampaign(), payload.getPinIdCampaignWave()));
    }

    public void updateReportNotes(ReportNoteRequest request){
        storedProcedureRepository.updateReportNotes(
                request.getPinIdCampaign(),
                request.getPinIdCampaignWave(),
                request.getPinIdQuestion(),
                request.getPinTxtSectionName(),
                request.getPinTxtAuthorId(),
                request.getPinTxtNote(),
                request.getPinDatActionDue(),
                request.getPinFlgIncludeInReport(),
                request.getPinTxtResolutionNote()
        );

    }
}

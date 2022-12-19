package com.datawise.satisactual.service;

import com.datawise.satisactual.model.FieldSurveyRequest;
import com.datawise.satisactual.repository.StoredProcedureRepository;
import com.datawise.satisactual.repository.UserMasterRepository;
import com.datawise.satisactual.utils.Const;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

@Service
public class SubmitFieldSurveyService {

    @Autowired
    private StoredProcedureRepository storedProcedureRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;

    public JSONObject submitFieldSurvey(FieldSurveyRequest request) {

        JSONObject response = new JSONObject();
        if (Objects.isNull(request)) return response(response);

        AtomicInteger count = new AtomicInteger(0);
        request.getFieldSurveyResponseDetails().forEach(surveyResponse -> {
            if (count.get() == 0) {
                storedProcedureRepository.submitFiledSurveyList(
                        returnPlaceholderIfNull.apply(request.getIdCampaign(), ""),
                        returnPlaceholderIfNull.apply(request.getIdCampaignWave(), ""),
                        returnPlaceholderIfNull.apply(request.getIdResponse(), ""),
                        returnPlaceholderIfNull.apply(request.getNumContactSequence(), ""),
                        returnPlaceholderIfNull.apply(surveyResponse.getIdQuestion(), "0"),
                        returnPlaceholderIfNull.apply(surveyResponse.getCodOptionSelected(), ""),
                        returnPlaceholderIfNull.apply(surveyResponse.getTxtQuestionResponse(), ""),
                        returnPlaceholderIfNull.apply(request.getCodLanguage(), ""),
                        returnPlaceholderIfNull.apply(request.getDatTimeSurveyStart(), ""),
                        returnPlaceholderIfNull.apply(request.getNumLatitudeStart(), ""),
                        returnPlaceholderIfNull.apply(request.getNumLongitudeStart(), ""),
                        returnPlaceholderIfNull.apply(request.getDatTimeSurveyEnd(), ""),
                        returnPlaceholderIfNull.apply(request.getNumLatitudeEnd(), ""),
                        returnPlaceholderIfNull.apply(request.getNumLongitudeEnd(), ""),
                        returnPlaceholderIfNull.apply(request.getIdContactList(), ""),
                        returnPlaceholderIfNull.apply(request.getNumListItem(), ""),
                        returnPlaceholderIfNull.apply(request.getTxtSurveyConductedBy(), ""),
                        returnPlaceholderIfNull.apply(request.getTxtDeviceId(), ""),
                        returnPlaceholderIfNull.apply(request.getTxtResponderName(), ""),
                        returnPlaceholderIfNull.apply(request.getNumResponderAge(), ""),
                        returnPlaceholderIfNull.apply(request.getEnuResponderGender(), ""),
                        returnPlaceholderIfNull.apply(request.getFlgAnonymousResponse(), ""),
                        returnPlaceholderIfNull.apply(request.getFlgAllowCLARIfContact(), ""),
                        returnPlaceholderIfNull.apply(surveyResponse.getBinRecordingPath(), "")
                );
            } else {
                storedProcedureRepository.submitResponseDetails(
                        Integer.parseInt(request.getIdCampaign()),
                        Integer.parseInt(request.getIdCampaignWave()),
                        request.getIdResponse().equals("0") || request.getIdResponse().equals("") ? -1 : Integer.parseInt(request.getIdResponse()),
                        Long.parseLong(surveyResponse.getIdQuestion()),
                        surveyResponse.getCodOptionSelected(),
                        surveyResponse.getTxtQuestionResponse(),
                        Integer.parseInt(surveyResponse.getNumRank()),
                        surveyResponse.getBinRecordingPath()
                );
            }
        });

        List<Tuple> tuples = userMasterRepository.findFieldSurvey(request.getIdCampaign(), request.getIdCampaignWave(), request.getIdResponse());
        if (CollectionUtils.isEmpty(tuples)) {
            return response(response);
        }

        Tuple tuple = tuples.get(0);
        response.put("num_answers_recorded", tuple.get("num_questions_answered"));
        response.put("enu_response_status", tuple.get("enu_response_status"));
        response.put("id_response", tuple.get("id_response"));
        response.put("cod_status", Const.INDICATOR_1);
        return response;
    }

    private JSONObject response(JSONObject response) {
        response.put("cod_status", Const.INDICATOR_MINUS_1);
        response.put("num_answers_recorded", "0");
        response.put("enu_response_status", "N");
        response.put("id_response", "-1");
        return response;
    }

    BiFunction<String, String, String> returnPlaceholderIfNull = (val, placeholder) -> {
        if (Objects.isNull(val)) return placeholder;
        else return val;
    };
}

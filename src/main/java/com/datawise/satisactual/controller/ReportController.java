package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.model.ReportNoteDetails;
import com.datawise.satisactual.model.ReportNoteRequest;
import com.datawise.satisactual.model.ReportRequest;
import com.datawise.satisactual.service.ReportService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/report")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<List<Map<String, Object>>>> findReports(@RequestBody ReportRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.findReports(request));
    }

    @PostMapping("/get-report-notes")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<ReportNoteDetails>> findReportNoteDetails(@RequestBody CommonPayload request) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.findReportNoteDetails(request));
    }

    @PostMapping("/ins-upd-report-notes")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<String> updateReportNotes(@RequestBody ReportNoteRequest request) {
        reportService.updateReportNotes(request);
        return ResponseEntity.status(HttpStatus.OK).body("success");
    }
}

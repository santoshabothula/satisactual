package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.FieldSurveyRequest;
import com.datawise.satisactual.service.SubmitFieldSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubmitFieldSurveyController {

    @Autowired
    private SubmitFieldSurveyService service;

    @PostMapping("/dat-tab/submit-field-survey")
    public ResponseEntity<String> submitFieldSurvey(@RequestBody FieldSurveyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.submitFieldSurvey(request).toString());
    }

}

package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.FieldSurveyRequest;
import com.datawise.satisactual.service.SubmitFieldSurveyService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SubmitFieldSurveyController {

    @Autowired
    private SubmitFieldSurveyService service;

    @PostMapping(value = "/dat-tab/submit-field-survey", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<String> submitFieldSurvey(@Valid @RequestBody FieldSurveyRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.submitFieldSurvey(request).toString());
    }

}

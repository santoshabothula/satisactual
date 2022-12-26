package com.datawise.satisactual.controller;

import com.datawise.satisactual.service.QuestionnaireService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService service;

    @GetMapping(value = "/dat-tab/questionnaires", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<String> getQuestionnaire(
            @RequestParam("u") String username,
            @RequestParam("q") String questionId,
            @RequestParam(value = "lang", required = false) String lang
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getQuestionnaire(username, questionId, lang).toString());
    }
}

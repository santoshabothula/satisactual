package com.datawise.satisactual.controller;

import com.datawise.satisactual.service.QuestionnaireService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService service;

    @GetMapping("/dat-tab/questionnaires")
    public ResponseEntity<String> getQuestionnaire(
            @RequestParam("u") String username,
            @RequestParam("q") String questionId,
            @RequestParam(value = "lang", required = false) String lang
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getQuestionnaire(username, questionId, lang).toString());
    }
}

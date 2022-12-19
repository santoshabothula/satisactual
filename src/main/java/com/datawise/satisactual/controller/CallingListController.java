package com.datawise.satisactual.controller;

import com.datawise.satisactual.service.CallingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallingListController {

    @Autowired
    private CallingListService service;

    @GetMapping("/dat-tab/calling-list")
    public ResponseEntity<String> getCallingList(@RequestParam("u") String pTxtUserID) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCallingList(pTxtUserID).toString());
    }
}

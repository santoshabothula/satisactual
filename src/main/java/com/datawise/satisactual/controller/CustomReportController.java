package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.custom.report.CustomReportRequest;
import com.datawise.satisactual.service.CustomReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController("/custom-report")
public class CustomReportController {

    @Autowired
    private CustomReportService service;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody CustomReportRequest request) {
        service.save(request);
        return ResponseEntity.status(HttpStatus.OK).body("Custom Report save operation completed successfully!");
    }

    @GetMapping("/{id-campaign}/{id-campaign-wave}/{report-owner-id}/{start-date}/{end-date}")
    public void get(Long campaign, Long wave, String ownerId, LocalDate startDate, LocalDate endDate) {

    }
}

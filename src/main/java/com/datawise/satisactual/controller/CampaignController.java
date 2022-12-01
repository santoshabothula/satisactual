package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CampaignDetails;
import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.service.StoredProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private StoredProcedureService service;

    @PostMapping
    public ResponseEntity<List<CampaignDetails>> getCampaigns(@RequestBody CommonPayload payload) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCampaign(payload.getPinTxtLoginId()));
    }

}

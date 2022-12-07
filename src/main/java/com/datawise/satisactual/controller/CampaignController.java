package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CampaignDetails;
import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.service.StoredProcedureService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campaigns")
@CrossOrigin
public class CampaignController {

    @Autowired
    private StoredProcedureService service;

    @PostMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CampaignDetails>> getCampaigns(@RequestBody CommonPayload payload) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCampaign(payload.getPinTxtLoginId()));
    }

}

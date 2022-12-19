package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CampaignDetails;
import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.service.CampaignService;
import com.datawise.satisactual.service.StoredProcedureService;
import io.swagger.annotations.ApiImplicitParam;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CampaignController {

    @Autowired
    private StoredProcedureService service;
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/campaigns")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CampaignDetails>> getCampaigns(@RequestBody CommonPayload payload) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCampaign(payload.getPinTxtLoginId()));
    }

    @GetMapping("/dat-tab/campaigns")
    public ResponseEntity<String> getCampaigns(@RequestParam("u") String username) {
        return ResponseEntity.status(HttpStatus.OK).body(campaignService.getCampaigns(username).toString());
    }
}

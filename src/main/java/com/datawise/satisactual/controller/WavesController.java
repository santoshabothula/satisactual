package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CommonPayload;
import com.datawise.satisactual.model.WaveDetails;
import com.datawise.satisactual.service.StoredProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/waves")
public class WavesController {

    @Autowired
    private StoredProcedureService service;

    @PostMapping
    public ResponseEntity<List<WaveDetails>> getWaves(@RequestBody CommonPayload payload) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findWaves(payload.getPinIdCampaign()));
    }
}

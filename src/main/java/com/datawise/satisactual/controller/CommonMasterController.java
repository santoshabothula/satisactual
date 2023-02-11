package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.CommonMasterRequest;
import com.datawise.satisactual.model.CustomMasterResponse;
import com.datawise.satisactual.service.CommonMasterService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mst")
public class CommonMasterController {

    @Autowired
    @Qualifier("master-service")
    private CommonMasterService service;

    @GetMapping("/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<Map<String, Object>>> getAllActive(@PathVariable("table-name") String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllActive(tableName));
    }

    @PostMapping("/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<Map<String, Object>>> getActive(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getActive(tableName, request.getIds()));
    }

    @GetMapping("/authorize/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<Map<String, Object>>> getAllNeedAuthorize(@PathVariable("table-name") String tableName) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllNeedAuthorize(tableName));
    }

    @PostMapping("/save/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CustomMasterResponse>> save(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.save(tableName, request));
    }

    @PutMapping("/update/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CustomMasterResponse>> update(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(tableName, request));
    }

    @DeleteMapping("/delete/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CustomMasterResponse>> delete(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(tableName, request));
    }

    @PostMapping("/reopen/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CustomMasterResponse>> reopen(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.reopen(tableName, request));
    }

    @PostMapping("/authorize/{table-name}")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<List<CustomMasterResponse>> authorize(
            @PathVariable("table-name") String tableName,
            @RequestBody CommonMasterRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(service.authorize(tableName, request));
    }

}

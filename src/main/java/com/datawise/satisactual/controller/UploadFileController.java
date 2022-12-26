package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.UploadFileRequest;
import com.datawise.satisactual.service.UploadFileService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UploadFileController {

    @Autowired
    private UploadFileService service;

    @PostMapping(path = "/dat-tab/upload-file-common", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", dataTypeClass = String.class, example = "access_token")
    public ResponseEntity<String> uploadFile(UploadFileRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(service.uploadFile(request, request.getFile()).toString());
    }
}
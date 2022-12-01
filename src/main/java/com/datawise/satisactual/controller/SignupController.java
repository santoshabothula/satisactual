package com.datawise.satisactual.controller;

import com.datawise.satisactual.model.SignupRequest;
import com.datawise.satisactual.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @Autowired
    private SignupService signupService;

    @PostMapping
    public void signup(@RequestBody SignupRequest request) throws NoSuchAlgorithmException {
        ResponseEntity.status(HttpStatus.OK).body(signupService.signup(request));
    }
}

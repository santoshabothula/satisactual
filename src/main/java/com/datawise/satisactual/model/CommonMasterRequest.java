package com.datawise.satisactual.model;

import lombok.Data;

import java.util.Map;

@Data
public class CommonMasterRequest {
    Map<String, Object> ids;
    Map<String, Object> body;
}

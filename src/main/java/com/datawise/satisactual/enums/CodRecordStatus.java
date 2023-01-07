package com.datawise.satisactual.enums;

import lombok.Getter;

@Getter
public enum CodRecordStatus {

    A("Approve"), N("New"), M("Modify"), R("Reopen"), C("Close"), X("Delete");

    private final String value;

    CodRecordStatus(String value) {
        this.value = value;
    }
}

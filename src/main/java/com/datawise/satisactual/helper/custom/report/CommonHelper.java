package com.datawise.satisactual.helper.custom.report;

import org.springframework.security.core.context.SecurityContextHolder;

public class CommonHelper {

    public static String getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

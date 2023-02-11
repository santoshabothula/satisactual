package com.datawise.satisactual.utils;

import com.datawise.satisactual.model.CustomMasterResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class DataTypeValidator {

    /**
     * @param errors errors object
     * @param datatype type column
     * @param k column name
     * @param v column value
     */
    public static void validateType(List<CustomMasterResponse> errors, String datatype, String k, Object v) {
        if (Objects.isNull(datatype)) {
            errors.add(CustomMasterResponse.builder().column(k).message("Datatype not configured!").code("DATATYPE").build());
        } else if (datatype.toLowerCase().contains("bigint") || datatype.toLowerCase().contains("smallint") || datatype.toLowerCase().contains("int") || datatype.toLowerCase().contains("mediumint")) {
            try {
                Integer.valueOf(String.valueOf(v));
            } catch (Exception ex) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ")").code("DATATYPE").build());
            }
        } else if (datatype.toLowerCase().contains("datetime")) {
            try {
                LocalDate.parse(String.valueOf(v), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            } catch (Exception ex) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ", format = yyyy-MM-dd HH:mm:ss)").code("DATATYPE").build());
            }
        } else if (datatype.toLowerCase().contains("date")) {
            try {
                LocalDate.parse(String.valueOf(v), DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (Exception ex) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ", format = YYYY-MM-DD)").code("DATATYPE").build());
            }
        } else if (datatype.toLowerCase().contains("double")) {
            try {
                Double.valueOf(String.valueOf(v));
            } catch (Exception ex) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ")").code("DATATYPE").build());
            }
        } else if (datatype.toLowerCase().contains("enum")) {
            if (!datatype.contains("'" + v + "'")) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ")").code("DATATYPE").build());
            }
        } else if (datatype.toLowerCase().contains("float")) {
            try {
                Float.valueOf(String.valueOf(v));
            } catch (Exception ex) {
                errors.add(CustomMasterResponse.builder().column(k).message("Datatype mismatch (required type = " + datatype + ")").code("DATATYPE").build());
            }
        }
    }
}

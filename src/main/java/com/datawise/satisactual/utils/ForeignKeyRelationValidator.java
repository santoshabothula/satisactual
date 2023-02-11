package com.datawise.satisactual.utils;

import com.datawise.satisactual.model.CommonMasterRequest;
import com.datawise.satisactual.model.CustomMasterResponse;
import com.datawise.satisactual.repository.CommonMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.util.*;

@Component
@ConfigurationProperties(prefix = "foreign-key-relations")
public class ForeignKeyRelationValidator {

    private Map<String, Map<String, List<String>>> mappings;

    @Autowired
    private CommonMasterRepository repository;

    public Map<String, Map<String, List<String>>> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Map<String, List<String>>> mappings) {
        this.mappings = mappings;
    }

    public List<CustomMasterResponse> validate(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();

        if (Objects.isNull(mappings) || mappings.isEmpty()) return errors;

        Map<String, List<String>> columns = mappings.get(tableName);
        if (Objects.isNull(columns) || columns.isEmpty()) return errors;

        columns.forEach((column, tables) -> {
            Object value = request.getIds().get(column);
            tables.forEach(table -> {
                Map<String, Object> params = new HashMap<>();
                params.put(column, value);
                List<Tuple> list = repository.getActive(table, "  " + column + " = " + ":" + column, params);
                if (Objects.nonNull(list) && !list.isEmpty()) {
                    errors.add(CustomMasterResponse.builder().code("FOREIGN_KEY_VALIDATION").message("Delete request failed. Reason: Relation exists with table " + table).build());
                }
            });
        });

        return errors;
    }
}

package com.datawise.satisactual.service;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.CommonMasterRequest;
import com.datawise.satisactual.model.CustomMasterResponse;
import com.datawise.satisactual.repository.CommonMasterRepository;
import com.datawise.satisactual.utils.CommonMasterValidator;
import com.datawise.satisactual.utils.ForeignKeyRelationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.time.LocalDateTime;
import java.util.*;

@Service("master-service")
public class CommonMasterService {

    @Autowired
    private CommonMasterRepository repository;
    @Autowired
    private CommonMasterValidator validator;
    @Autowired
    private ForeignKeyRelationValidator foreignKeyRelationValidator;
    @Autowired
    private AuditTrailService audit;

    public List<Map<String, Object>> getAllActive(String tableName) {
        List<Map<String, Object>> response = new ArrayList<>();
        List<Tuple> result = repository.getAllActive(tableName);
        for (Tuple row: result) {
            Map<String, Object> map = new HashMap<>();
            List<TupleElement<?>> columns = row.getElements();
            for (TupleElement<?> column : columns) {
                map.put(column.getAlias(), row.get(column));
            }
            response.add(map);
        }
        return response;
    }

    public List<Map<String, Object>> getActive(String tableName, Map<String, Object> ids) {
        List<Map<String, Object>> response = new ArrayList<>();

        if (Objects.isNull(ids) || ids.isEmpty()) return response;

        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        ids.forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> result = repository.getActive(tableName, idClause, params);
        for (Tuple row: result) {
            Map<String, Object> map = new HashMap<>();
            List<TupleElement<?>> columns = row.getElements();
            for (TupleElement<?> column : columns) {
                map.put(column.getAlias(), row.get(column));
            }
            response.add(map);
        }
        return response;
    }

    public List<Map<String, Object>> getAllNeedAuthorize(String tableName) {
        List<Map<String, Object>> response = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", SecurityContextHolder.getContext().getAuthentication().getName());

        List<Tuple> result = repository.getAllNeedAuthorize(tableName, params);
        for (Tuple row: result) {
            Map<String, Object> map = new HashMap<>();
            List<TupleElement<?>> columns = row.getElements();
            for (TupleElement<?> column : columns) {
                map.put(column.getAlias(), row.get(column));
            }
            response.add(map);
        }
        return response;
    }

    public List<CustomMasterResponse> save(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validator.validateNull(request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDataType(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateId(tableName, request);
        if (!errors.isEmpty()) return errors;

        Map<String, Object> params = new HashMap<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        request.getIds().forEach((k,v) -> {
            columns.append(k).append(",");
            values.append(":").append(k).append(",");
            params.put(k, v);
        });
        request.getBody().forEach((k,v) -> {
            columns.append(k).append(",");
            values.append(":").append(k).append(",");
            params.put(k, v);
        });

        columns.append("cod_rec_status").append(",");
        values.append(":").append("cod_rec_status").append(",");
        params.put("cod_rec_status", CodRecordStatus.N.name());

        columns.append("txt_last_maker_id").append(",");
        values.append(":").append("txt_last_maker_id").append(",");
        params.put("txt_last_maker_id", SecurityContextHolder.getContext().getAuthentication().getName());

        columns.append("dat_last_maker").append(",");
        values.append(":").append("dat_last_maker").append(",");
        params.put("dat_last_maker", LocalDateTime.now());

        String columnsList = columns.substring(0, columns.lastIndexOf(","));
        String valuesList = values.substring(0, values.lastIndexOf(","));
        repository.save(tableName, columnsList, valuesList, params);
        audit.save(tableName, CodRecordStatus.N.name(), request.getIds());
        // audit

        // auto authorize
        if (validator.isAutoAuthorize(tableName)) {
            params.clear();
            StringBuilder sb = new StringBuilder();
            request.getIds().forEach((k,v) -> {
                sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
                params.put(k, v);
            });
            String idClause = sb.substring(0, sb.lastIndexOf("AND"));

            params.put("checkerId", "SYSTEM");
            params.put("checkerDate", LocalDateTime.now());

            repository.authorize(tableName, idClause, params);
            audit.save(tableName, CodRecordStatus.A.name(), request.getIds());
        }

        errors.add(CustomMasterResponse.builder().code("SAVE").message("Save operation completed successfully").build());
        return errors;
    }

    public List<CustomMasterResponse> update(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validator.validateNull(request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDataType(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateUpdateId(tableName, request);
        if (!errors.isEmpty()) return errors;

        Map<String, Object> params = new HashMap<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        request.getIds().forEach((k,v) -> {
            columns.append(k).append(",");
            values.append(":").append(k).append(",");
            params.put(k, v);
        });
        request.getBody().forEach((k,v) -> {
            columns.append(k).append(",");
            values.append(":").append(k).append(",");
            params.put(k, v);
        });

        columns.append("cod_rec_status").append(",");
        values.append(":").append("cod_rec_status").append(",");
        params.put("cod_rec_status", CodRecordStatus.M.name());

        columns.append("txt_last_maker_id").append(",");
        values.append(":").append("txt_last_maker_id").append(",");
        params.put("txt_last_maker_id", SecurityContextHolder.getContext().getAuthentication().getName());

        columns.append("dat_last_maker").append(",");
        values.append(":").append("dat_last_maker").append(",");
        params.put("dat_last_maker", LocalDateTime.now());

        String columnsList = columns.substring(0, columns.lastIndexOf(","));
        String valuesList = values.substring(0, values.lastIndexOf(","));
        repository.save(tableName, columnsList, valuesList, params);
        audit.save(tableName, CodRecordStatus.M.name(), request.getIds());

        if (validator.isAutoAuthorize(tableName)) {
            params.clear();
            StringBuilder sb = new StringBuilder();
            request.getIds().forEach((k,v) -> {
                sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
                params.put(k, v);
            });
            String idClause = sb.substring(0, sb.lastIndexOf("AND"));

            List<Tuple> activeRecord = repository.getActive(tableName, idClause, params);
            Optional<Tuple> activeOpt = activeRecord.stream().filter(o -> CodRecordStatus.A.name().equalsIgnoreCase(String.valueOf(o.get("cod_rec_status")))).findFirst();
            if (activeOpt.isPresent()) {
                repository.deleteActiveRecord(tableName, idClause, params);
            }

            params.put("checkerId", "SYSTEM");
            params.put("checkerDate", LocalDateTime.now());

            repository.authorize(tableName, idClause, params);
            audit.save(tableName, CodRecordStatus.A.name(), request.getIds());
        }

        errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update operation completed successfully").build());
        return errors;
    }

    public List<CustomMasterResponse> delete(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validator.validateIdNull(request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDataType(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDeleteId(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = foreignKeyRelationValidator.validate(tableName, request);
        if (!errors.isEmpty()) return errors;

        StringBuilder sb = new StringBuilder();
        Map<String, Object> idParams = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            idParams.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> result = repository.getActive(tableName, idClause, idParams);
        Tuple active = result.get(0);
        if (Objects.nonNull(active)) {
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            List<TupleElement<?>> tupleColumns = active.getElements();
            for (TupleElement<?> column : tupleColumns) {
                columns.append(column.getAlias()).append(",");
                values.append(":").append(column.getAlias()).append(",");

                if ("cod_rec_status".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), CodRecordStatus.X.name());
                } else if ("txt_last_maker_id".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), SecurityContextHolder.getContext().getAuthentication().getName());
                } else if ("dat_last_maker".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), LocalDateTime.now());
                } else if ("txt_last_checker_id".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), null);
                } else if ("dat_last_checker".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), null);
                } else {
                    params.put(column.getAlias(), active.get(column));
                }
            }

            String columnsList = columns.substring(0, columns.lastIndexOf(","));
            String valuesList = values.substring(0, values.lastIndexOf(","));
            repository.save(tableName, columnsList, valuesList, params);
            audit.save(tableName, CodRecordStatus.X.name(), request.getIds());

            if (validator.isAutoAuthorize(tableName)) {
                Optional<Tuple> activeOpt = result.stream().filter(o -> CodRecordStatus.A.name().equalsIgnoreCase(String.valueOf(o.get("cod_rec_status")))).findFirst();
                if (activeOpt.isPresent()) {
                    repository.deleteActiveRecord(tableName, idClause, idParams);
                }

                idParams.put("checkerId", "SYSTEM");
                idParams.put("checkerDate", LocalDateTime.now());

                repository.deleteAuthorize(tableName, idClause, idParams);
                audit.save(tableName, CodRecordStatus.C.name(), request.getIds());
            }
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Delete operation completed successfully").build());
        }
        return errors;
    }

    public List<CustomMasterResponse> reopen(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validator.validateIdNull(request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDataType(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDeleteIdForReopen(tableName, request);
        if (!errors.isEmpty()) return errors;

        StringBuilder sb = new StringBuilder();
        Map<String, Object> idParams = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            idParams.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> result = repository.validateDeleteId(tableName, idClause, idParams);
        Optional<Tuple> activeOpt = result.stream().filter(o -> CodRecordStatus.C.name().equalsIgnoreCase(String.valueOf(o.get("cod_rec_status")))).findFirst();
        if (activeOpt.isPresent()) {
            Tuple active = activeOpt.get();
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();

            List<TupleElement<?>> tupleColumns = active.getElements();
            for (TupleElement<?> column : tupleColumns) {
                columns.append(column.getAlias()).append(",");
                values.append(":").append(column.getAlias()).append(",");

                if ("cod_rec_status".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), CodRecordStatus.R.name());
                } else if ("txt_last_maker_id".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), SecurityContextHolder.getContext().getAuthentication().getName());
                } else if ("dat_last_maker".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), LocalDateTime.now());
                } else if ("txt_last_checker_id".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), null);
                } else if ("dat_last_checker".equalsIgnoreCase(column.getAlias())) {
                    params.put(column.getAlias(), null);
                } else {
                    params.put(column.getAlias(), active.get(column));
                }
            }

            String columnsList = columns.substring(0, columns.lastIndexOf(","));
            String valuesList = values.substring(0, values.lastIndexOf(","));
            repository.save(tableName, columnsList, valuesList, params);
            audit.save(tableName, CodRecordStatus.R.name(), request.getIds());

            if (validator.isAutoAuthorize(tableName)) {
                repository.deleteDeletedRecord(tableName, idClause, idParams);

                idParams.put("checkerId", "SYSTEM");
                idParams.put("checkerDate", LocalDateTime.now());

                repository.authorize(tableName, idClause, idParams);
                audit.save(tableName, CodRecordStatus.A.name(), request.getIds());
            }
            errors.add(CustomMasterResponse.builder().code("REOPEN").message("Reopen operation completed successfully").build());
        }

        return errors;
    }

    public List<CustomMasterResponse> authorize(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validator.validateIdNull(request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateDataType(tableName, request);
        if (!errors.isEmpty()) return errors;

        errors = validator.validateAuthorizeId(tableName, request);
        if (!errors.isEmpty()) return errors;

        StringBuilder sb = new StringBuilder();
        Map<String, Object> idParams = new HashMap<>();
        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            idParams.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> result = repository.validateAuthorizeId(tableName, idClause, idParams);
        Optional<Tuple> activeOpt = result.stream().filter(o -> CodRecordStatus.C.name().equalsIgnoreCase(String.valueOf(o.get("cod_rec_status")))).findFirst();
        idParams.put("checkerId", SecurityContextHolder.getContext().getAuthentication().getName());
        idParams.put("checkerDate", LocalDateTime.now());
        if (activeOpt.isPresent()) {
            repository.deleteAuthorize(tableName, idClause, idParams);
            audit.save(tableName, CodRecordStatus.C.name(), request.getIds());
        } else {
            repository.authorize(tableName, idClause, idParams);
            audit.save(tableName, CodRecordStatus.A.name(), request.getIds());
        }

        errors.add(CustomMasterResponse.builder().code("AUTHORIZE").message("Authorize operation completed successfully").build());
        return errors;
    }
}

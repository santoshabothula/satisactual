package com.datawise.satisactual.utils;

import com.datawise.satisactual.entities.MasterTableColumnEntity;
import com.datawise.satisactual.entities.MasterTableEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.model.CommonMasterRequest;
import com.datawise.satisactual.model.CustomMasterResponse;
import com.datawise.satisactual.repository.CommonMasterRepository;
import com.datawise.satisactual.repository.MasterTableColumnRepository;
import com.datawise.satisactual.repository.MasterTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CommonMasterValidator {

    @Autowired
    private CommonMasterRepository commonMasterRepository;
    @Autowired
    private MasterTableRepository masterTableRepository;
    @Autowired
    private MasterTableColumnRepository masterTableColumnRepository;

    public List<CustomMasterResponse> validateIdNull(CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();
        if (Objects.isNull(request) || Objects.isNull(request.getIds()) || request.getIds().isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("REQ_NULL").message("Request body cannot be null!").build());
            return errors;
        }

        request.getIds().forEach((k,v) -> {
            if (Objects.isNull(v) || String.valueOf(v).length() == 0) {
                errors.add(CustomMasterResponse.builder().code("ID_NULL").column(k).message(k + " cannot be null!").build());
            }
        });
        return errors;
    }

    public List<CustomMasterResponse> validateNull(CommonMasterRequest request) {
        List<CustomMasterResponse> errors = validateIdNull(request);

        if (Objects.isNull(request.getBody()) || request.getBody().isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("REQ_NULL").message("Request body cannot be null!").build());
        }

        return errors;
    }

    public List<CustomMasterResponse> validateDataType(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();

        List<MasterTableColumnEntity> columns = masterTableColumnRepository.findByIdTableNameAndIdCodRecStatus(tableName, CodRecordStatus.A.name());
        if (Objects.isNull(columns)) return errors;

        List<MasterTableColumnEntity> primaryColumns = columns.stream().filter(o -> Const.INDICATOR_Y.equalsIgnoreCase(o.getIsPrimaryKey())).collect(Collectors.toList());
        if (primaryColumns.isEmpty()) return errors;

        if (primaryColumns.size() != request.getIds().size()) {
            errors.add(CustomMasterResponse.builder().code("ID_VALIDATION").message("Primary keys not matching").build());
        }

        if (!errors.isEmpty()) return errors;

        primaryColumns.forEach(col -> {
            if (request.getIds().keySet().stream().filter(o -> o.equalsIgnoreCase(col.getId().getColumnName())).findAny().isEmpty()) {
                errors.add(CustomMasterResponse.builder().code("ID_VALIDATION").message("Property (" + col.getId().getColumnName() + ") not matching / not found with primary keys").build());
            }
        });

        if (!errors.isEmpty()) return errors;

        request.getIds().forEach((k,v) -> {
            columns.stream().filter(column -> k.equalsIgnoreCase(column.getId().getColumnName())).findFirst().ifPresent(column -> {
                DataTypeValidator.validateType(errors, column.getColumnDatatype(), k, v);
            });
        });

        if (Objects.nonNull(request.getBody()) && !request.getBody().isEmpty()) {
            request.getBody().forEach((k,v) -> {
                columns.stream().filter(column -> k.equalsIgnoreCase(column.getId().getColumnName())).findFirst().ifPresent(column -> {
                    DataTypeValidator.validateType(errors, column.getColumnDatatype(), k, v);
                });
            });
        }

        return errors;
    }

    public List<CustomMasterResponse> validateId(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> list = commonMasterRepository.validateId(tableName, idClause, params);
        if (Objects.isNull(list) || list.isEmpty()) return errors;

        errors.add(CustomMasterResponse.builder().code("SAVE").message("Save operation failed, Reason: Record already exists with status - " + list.get(0).get("cod_rec_status")).build());
        return errors;
    }

    public List<CustomMasterResponse> validateUpdateId(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> list = commonMasterRepository.validateUpdateId(tableName, idClause, params);
        if (Objects.isNull(list) || list.isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update operation failed, Reason: Approved record does not exists").build());
            return errors;
        }

        List<Object> values = list.stream().map(o -> o.get("cod_rec_status")).collect(Collectors.toList());
        if (values.size() == 1 && !CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update operation failed, Reason: Approved record does not exists").build());
            return errors;
        }

        boolean isActiveFound = false;
        boolean isModifyFound = false;
        boolean isDeleteFound = false;
        boolean isReopenFound = false;
        boolean isOtherStatusFound = false;
        for (Object status: values) {
            if (String.valueOf(status).equals(CodRecordStatus.A.name())) {
                isActiveFound = true;
            } else if (String.valueOf(status).equals(CodRecordStatus.M.name())) {
                isModifyFound = true;
            } else if (String.valueOf(status).equals(CodRecordStatus.X.name())) {
                isDeleteFound = true;
            } else if (String.valueOf(status).equals(CodRecordStatus.R.name())) {
                isReopenFound = true;
            } else isOtherStatusFound = true;
        }

        if (isOtherStatusFound || !isActiveFound) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update operation failed, Reason: Approved record does not exists").build());
        } else if (isModifyFound) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update Record request already initiated (Current Status: waiting for approval)").build());
        } else if (isDeleteFound) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update Record failed (Reason: Delete Record request initiated)").build());
        } else if (isReopenFound) {
            errors.add(CustomMasterResponse.builder().code("UPDATE").message("Update Record failed (Reason: Reopen Record request initiated)").build());
        }

        return errors;
    }

    public List<CustomMasterResponse> validateDeleteId(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> list = commonMasterRepository.validateUpdateId(tableName, idClause, params);
        if (Objects.isNull(list) || list.isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete operation failed, Reason: Approved record does not exists").build());
            return errors;
        }

        List<Object> values = list.stream().map(o -> o.get("cod_rec_status")).collect(Collectors.toList());
        if (values.size() == 1 && !CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) {
            errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete operation failed, Reason: Approved record does not exists").build());
            return errors;
        }

        for (Object status: values) {
            if (String.valueOf(status).equals(CodRecordStatus.M.name())) {
                errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete Record failed (Reason: Updated Record request initiated)").build());
            } else if (String.valueOf(status).equals(CodRecordStatus.X.name())) {
                errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete Record failed (Reason: Already Delete Record request initiated)").build());
            } else if (String.valueOf(status).equals(CodRecordStatus.R.name())) {
                errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete Record failed (Reason: Reopen Record request initiated)").build());
            } else if (String.valueOf(status).equals(CodRecordStatus.N.name())) {
                errors.add(CustomMasterResponse.builder().code("DELETE").message("Delete operation failed, Reason: Approved record does not exists").build());
            } else if (String.valueOf(status).equals(CodRecordStatus.C.name())) {
                errors.add(CustomMasterResponse.builder().code("DELETE").message("Record Already in deleted state!").build());
            }
        }

        return errors;
    }

    public List<CustomMasterResponse> validateDeleteIdForReopen(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> list = commonMasterRepository.validateDeleteId(tableName, idClause, params);
        if (Objects.isNull(list) || list.isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("REOPEN").message("Reopen operation failed, Reason: Deleted record not found").build());
        }
        return errors;
    }

    public List<CustomMasterResponse> validateAuthorizeId(String tableName, CommonMasterRequest request) {
        List<CustomMasterResponse> errors = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        request.getIds().forEach((k,v) -> {
            sb.append("  ").append(k).append(" = ").append(":").append(k).append(" AND ");
            params.put(k, v);
        });
        String idClause = sb.substring(0, sb.lastIndexOf("AND"));

        List<Tuple> list = commonMasterRepository.validateAuthorizeId(tableName, idClause, params);
        if (Objects.isNull(list) || list.isEmpty()) {
            errors.add(CustomMasterResponse.builder().code("AUTHORIZE").message("Authorize operation failed, Reason: Record not found with statuses (N, M, X, R)").build());
            return errors;
        }

        if (list.size() != 1) {
            String statuses = list.stream().map(o -> String.valueOf(o.get("cod_rec_status"))).collect(Collectors.joining(","));
            errors.add(CustomMasterResponse.builder().code("AUTHORIZE").message("Authorize operation failed, Reason: Multiple records found with statuses (" + statuses + ")").build());
            return errors;
        }

        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        String maker = String.valueOf(list.get(0).get("txt_last_maker_id"));
        if (user.equalsIgnoreCase(maker)) {
            errors.add(CustomMasterResponse.builder().code("AUTHORIZE").message("Authorize operation failed, Reason: Maker and checker cannot be same").build());
        }

        return errors;
    }

    public boolean isAutoAuthorize(String tableName) {
        MasterTableEntity table = masterTableRepository.findByTableNameAndCodRecordStatus(tableName, CodRecordStatus.A.name());
        if (Objects.isNull(table)) return false;
        return Const.INDICATOR_Y.equalsIgnoreCase(table.getIsAutoAuth());
    }
}

package com.datawise.satisactual.repository.master;

import com.datawise.satisactual.entities.master.MasterTableEntity;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.enums.FlagYesNo;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.model.master.dto.BaseDTO;
import com.datawise.satisactual.repository.IBaseRepository;
import com.datawise.satisactual.repository.MasterTableRepository;
import com.datawise.satisactual.service.master.AuditTrailService;
import com.datawise.satisactual.utils.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommonMasterServiceHelper<ENTITY, ID, DTO> {

    @Autowired
    private MasterTableRepository masterTableRepository;
    @Autowired
    private AuditTrailService auditTrailService;
    @Autowired
    private ReflectionUtil reflectionUtil;

    public void saveValidation(List<ENTITY> list) {
        if (Objects.isNull(list) || list.isEmpty()) return;
        List<Object> values = reflectionUtil.getEntityPropValues("codRecordStatus", list);
        if (Objects.nonNull(values)) {
            String status = values.stream().map(v -> CodRecordStatus.valueOf(String.valueOf(v)).getValue()).collect(Collectors.joining(","));
            throw new SatisActualProcessException("Record Already Exists! with status: " + status);
        }
        throw new SatisActualProcessException("Record Already Exists!");
    }

    public void updateValidation(List<ENTITY> list) {
        if (Objects.isNull(list) || list.isEmpty()) throw new SatisActualProcessException("Active Record Not Found!");
        List<Object> values = reflectionUtil.getEntityPropValues("codRecordStatus", list);
        if (Objects.nonNull(values)) {
            if (values.size() == 1 && CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) return;
            else if (values.size() == 1 && !CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) {
                throw new SatisActualProcessException("Active Record Not Found!");
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
                throw new SatisActualProcessException("Active Record Not Found!");
            } else if (isModifyFound) {
                throw new SatisActualProcessException("Update Record request already initiated (Current Status: waiting for approval)");
            } else if (isDeleteFound) {
                throw new SatisActualProcessException("Update Record failed (Reason: Delete Record request initiated)");
            } else if (isReopenFound) {
                throw new SatisActualProcessException("Update Record failed (Reason: Reopen Record request initiated)");
            }
        }
    }

    public void deleteValidation(List<ENTITY> list) {
        if (Objects.isNull(list) || list.isEmpty()) throw new SatisActualProcessException("Active Record Not Found!");
        List<Object> values = reflectionUtil.getEntityPropValues("codRecordStatus", list);
        if (Objects.nonNull(values)) {
            if (values.size() == 1 && CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) return;
            else if (values.size() == 1 && !CodRecordStatus.A.name().equals(String.valueOf(values.get(0)))) {
                throw new SatisActualProcessException("Active Record Not Found!");
            }

            for (Object status: values) {
                if (String.valueOf(status).equals(CodRecordStatus.M.name())) {
                    throw new SatisActualProcessException("Delete Record failed (Reason: Updated Record request initiated)");
                } else if (String.valueOf(status).equals(CodRecordStatus.X.name())) {
                    throw new SatisActualProcessException("Delete Record failed (Reason: Already Delete Record request initiated)");
                } else if (String.valueOf(status).equals(CodRecordStatus.R.name())) {
                    throw new SatisActualProcessException("Delete Record failed (Reason: Reopen Record request initiated)");
                } else if (String.valueOf(status).equals(CodRecordStatus.N.name())) {
                    throw new SatisActualProcessException("Active Record Not Found!");
                } else if (String.valueOf(status).equals(CodRecordStatus.C.name())) {
                    throw new SatisActualProcessException("Record Already in deleted state!");
                }
            }
        }
    }

    public void reopenValidation(List<ENTITY> list) {
        if (Objects.isNull(list) || list.isEmpty()) throw new SatisActualProcessException("Record Not Found!");
        List<Object> values = reflectionUtil.getEntityPropValues("codRecordStatus", list);
        if (Objects.nonNull(values) && !values.contains(CodRecordStatus.C.name())) {
            throw new SatisActualProcessException("Record Not Found!");
        }
    }

    public void authorizeValidation(List<ENTITY> list) {
        if (Objects.isNull(list) || list.isEmpty()) throw new SatisActualProcessException("Record Not Found!");
        else if (list.size() > 1) throw new SatisActualProcessException("Invalid Record Found!");
    }

    @Transactional
    public boolean autoApprove(IBaseRepository<ENTITY, ID> rep, ENTITY oldEntity, ENTITY newEntity, String primaryKeyValue) {
        MasterTableEntity masterEntity = masterTableRepository.findByTableNameAndCodRecordStatus(reflectionUtil.getTableName(newEntity), CodRecordStatus.A.name());
        if (Objects.isNull(masterEntity)) return false;

        if (FlagYesNo.Y.name().equalsIgnoreCase(masterEntity.getIsAutoAuth())) {
            rep.delete(oldEntity);
            rep.save(newEntity);
            auditTrail(oldEntity, newEntity, CodRecordStatus.A, primaryKeyValue);
            return true;
        }
        return false;
    }

    @Async
    public void auditTrail(ENTITY oldEntity, ENTITY newEntity, CodRecordStatus status, String primaryKeyValue) {
        auditTrailService.audit(oldEntity, newEntity, status, primaryKeyValue);
    }

    public void makerChecker(BaseDTO dto, boolean isMaker, String checker) {
        if (isMaker) {
            dto.setLastMakerId(SecurityContextHolder.getContext().getAuthentication().getName());
            dto.setLastMakerDateTime(LocalDateTime.now());
        } else {
            dto.setLastCheckerId(checker);
            dto.setLastCheckerDateTime(LocalDateTime.now());
        }
    }

    public ENTITY updateEntityId(BaseDTO dto, Class<ENTITY> entityCls, ID id, ModelMapper mapper) {
        try {
            ENTITY entity = mapper.map(dto, entityCls);
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
            return entity;
        } catch (Exception ex) {
            throw new SatisActualProcessException("Failed to identify ID property " + ex.getMessage());
        }
    }

    public ID updateCodRecordStatus(ID id, CodRecordStatus status) {
        try {
            Field field = id.getClass().getDeclaredField("codRecordStatus");
            field.setAccessible(true);
            field.set(id, status.name());
            return id;
        } catch (Exception ex) {
            throw new SatisActualProcessException("Failed to identify ID property " + ex.getMessage());
        }
    }

    public Object getCodRecordStatus(ENTITY entity) {
        try {
            return reflectionUtil.getEntityValues(entity.getClass(), entity).get("codRecordStatus");
        } catch (Exception ex) {
            throw new SatisActualProcessException("Failed to identify ID property " + ex.getMessage());
        }
    }
}

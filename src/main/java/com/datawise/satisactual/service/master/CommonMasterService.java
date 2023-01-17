package com.datawise.satisactual.service.master;

import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.exception.SatisActualProcessException;
import com.datawise.satisactual.model.master.dto.BaseDTO;
import com.datawise.satisactual.repository.IBaseRepository;
import com.datawise.satisactual.repository.master.CommonMasterServiceHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommonMasterService<ENTITY, ID, DTO> {

    @Autowired
    private CommonMasterServiceHelper<ENTITY, ID, DTO> helper;

    @Transactional(readOnly = true)
    public List<DTO> getActiveAll(IBaseRepository<ENTITY, ID> rep, ModelMapper mapper, Class<DTO> dto) {
        return rep.findByIdCodRecordStatus(CodRecordStatus.A.name()).stream().map(e -> mapper.map(e, dto)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DTO getActive(IBaseRepository<ENTITY, ID> rep, ID id, ModelMapper mapper, Class<DTO> dto) {
        return rep.findById(id).map(e -> mapper.map(e, dto)).orElseThrow(() -> new SatisActualProcessException("Record not found!"));
    }

    @Transactional
    public void save(IBaseRepository<ENTITY, ID> rep, BaseDTO dto, Class<ENTITY> entityCls, Specification<ENTITY> spec, String primaryKeyValue, ModelMapper mapper, ID id, ID approveId) {
        List<ENTITY> entities = rep.findAll(spec);
        helper.saveValidation(entities);

        helper.makerChecker(dto, true, null);
        ENTITY entity = rep.save(helper.updateEntityId(dto, entityCls, id, mapper));
        helper.auditTrail(null, entity, CodRecordStatus.N, primaryKeyValue);

        helper.makerChecker(dto, false, "SYSTEM");
        helper.autoApprove(rep, entity, helper.updateEntityId(dto, entityCls, approveId, mapper), primaryKeyValue);
    }

    @Transactional
    public void update(IBaseRepository<ENTITY, ID> rep, BaseDTO dto, Class<ENTITY> entityCls, Specification<ENTITY> spec, String primaryKeyValue, ModelMapper mapper, ID id, ID approveId) {
        List<ENTITY> entities = rep.findAll(spec);
        helper.updateValidation(entities);

        helper.makerChecker(dto, true, null);
        ENTITY entity = rep.save(helper.updateEntityId(dto, entityCls, id, mapper));
        helper.auditTrail(entities.get(0), entity, CodRecordStatus.M, primaryKeyValue);

        helper.makerChecker(dto, false, "SYSTEM");
        helper.autoApprove(rep, entity, helper.updateEntityId(dto, entityCls, approveId, mapper), primaryKeyValue);
    }

    @Transactional
    public void delete(IBaseRepository<ENTITY, ID> rep, Class<DTO> dtoClass, Class<ENTITY> entityCls, Specification<ENTITY> spec, String primaryKeyValue, ModelMapper mapper, ID id, ID approveId, ID deleteId) {
        List<ENTITY> entities = rep.findAll(spec);
        helper.deleteValidation(entities);

        BaseDTO dto = (BaseDTO) mapper.map(entities.get(0), dtoClass);
        helper.makerChecker(dto, true, null);
        ENTITY entity = rep.save(helper.updateEntityId(dto, entityCls, id, mapper));
        helper.auditTrail(entities.get(0), entity, CodRecordStatus.X, primaryKeyValue);

        helper.makerChecker(dto, false, "SYSTEM");
        if (helper.autoApprove(rep, entity, helper.updateEntityId(dto, entityCls, approveId, mapper), primaryKeyValue)) {
            rep.delete(helper.updateEntityId(dto, entityCls, deleteId, mapper));
        }
    }

    @Transactional
    public void reopen(IBaseRepository<ENTITY, ID> rep, Class<DTO> dtoClass, Class<ENTITY> entityCls, Specification<ENTITY> spec, String primaryKeyValue, ModelMapper mapper, ID id, ID approveId, ID deleteId) {
        List<ENTITY> entities = rep.findAll(spec);
        helper.reopenValidation(entities);

        BaseDTO dto = (BaseDTO) mapper.map(entities.get(0), dtoClass);
        helper.makerChecker(dto, true, null);
        ENTITY entity = rep.save(helper.updateEntityId(dto, entityCls, id, mapper));
        helper.auditTrail(entities.get(0), entity, CodRecordStatus.R, primaryKeyValue);

        helper.makerChecker(dto, false, "SYSTEM");
        if (helper.autoApprove(rep, entity, helper.updateEntityId(dto, entityCls, approveId, mapper), primaryKeyValue)) {
            rep.delete(helper.updateEntityId(dto, entityCls, deleteId, mapper));
        }
    }

    @Transactional
    public void authorize(IBaseRepository<ENTITY, ID> rep, Class<DTO> dtoClass, Class<ENTITY> entityCls, Specification<ENTITY> spec, String primaryKeyValue, ModelMapper mapper, ID id, ID deletetId) {
        List<ENTITY> entities = rep.findAll(spec);
        helper.authorizeValidation(entities);

        BaseDTO dto = (BaseDTO) mapper.map(entities.get(0), dtoClass);
        String checker = SecurityContextHolder.getContext().getAuthentication().getName();
        if (Objects.nonNull(dto.getLastMakerId()) && dto.getLastMakerId().equals(checker)) {
            throw new SatisActualProcessException("Maker and Checker cannot be same user!");
        }
        rep.delete(entities.get(0));

        CodRecordStatus codRecordStatus = CodRecordStatus.A;
        Object status = helper.getCodRecordStatus(entities.get(0));
        helper.makerChecker(dto, false, checker);

        boolean isDelete = false;
        if (CodRecordStatus.X.name().equals(status)) {
            rep.deleteById(id);
            codRecordStatus = CodRecordStatus.C;
            isDelete = true;
        } else if (CodRecordStatus.R.name().equals(status)) {
            rep.deleteById(deletetId);
        }

        ENTITY entity = rep.save(helper.updateEntityId(dto, entityCls, isDelete ? deletetId : id, mapper));
        helper.auditTrail(entities.get(0), entity, codRecordStatus, primaryKeyValue);
    }
}

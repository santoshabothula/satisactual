package com.datawise.satisactual.repository;

import com.datawise.satisactual.enums.CodRecordStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface IBaseRepository<ENTITY, ID> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY> {
    List<ENTITY> findByIdCodRecordStatus(String codRecordStatus);

    default Specification<ENTITY> findRecordWithStatusIn(List<CodRecordStatus> statuses) {
        return (e, cq, cb) -> (e.get("id").get("codRecordStatus").in(statuses.stream().map(CodRecordStatus::name).collect(Collectors.toList())));
    }

    default Specification<ENTITY> findRecordWithCode(String code, String key) {
        return (e, cq, cb) -> cb.equal(e.get("id").get(key), code);
    }
}

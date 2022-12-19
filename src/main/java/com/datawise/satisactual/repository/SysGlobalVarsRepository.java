package com.datawise.satisactual.repository;

import com.datawise.satisactual.entities.SysGlobalVars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysGlobalVarsRepository extends JpaRepository<SysGlobalVars, String> {
    List<SysGlobalVars> findByCodRecStatus(String codRecStatus);
}

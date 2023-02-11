package com.datawise.satisactual.service;

import com.datawise.satisactual.entities.MasterTableAuditTrailEmbeddedKey;
import com.datawise.satisactual.entities.MasterTableAuditTrailMaster;
import com.datawise.satisactual.enums.CodRecordStatus;
import com.datawise.satisactual.repository.MasterTableAuditTrailRepository;
import com.datawise.satisactual.utils.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AuditTrailService {

    @Autowired
    private MasterTableAuditTrailRepository repository;
    @Autowired
    private ReflectionUtil reflectionUtil;

    public void audit(Object oldEntity, Object newEntity, CodRecordStatus status, String primaryKeyValue) {
        Map<String, Object> values = reflectionUtil.getEntityValues(newEntity.getClass(), newEntity);
        String id = primaryKeyValue + ";" + status.name();
        String table = newEntity.getClass().getAnnotation(Table.class).name();
        if (Objects.isNull(oldEntity)) {
            save(id, table, status.getValue(), values);
            return;
        }

        Map<String, String> map =  diff(oldEntity, values);
        save(id, table, String.join("; ", map.values()), values);
    }

    private Map<String, String> diff(Object oldEntity, Map<String, Object> newValues) {
        Map<String, String> map = new LinkedHashMap<>();
        Map<String, Object> oldValues = reflectionUtil.getEntityValues(oldEntity.getClass(), oldEntity);
        oldValues.forEach((k,v) -> {
            if (!k.startsWith("get") && !String.valueOf(v).trim().equals(String.valueOf(newValues.get(k)).trim())) {
                map.put(k, k + " (Old value: " + v + ", New Value: " + newValues.get(k) + ")");
            }
        });
        return map;
    }

    @Transactional
    private void save(String id, String table, String status, Map<String, Object> map) {
        repository.save(
            MasterTableAuditTrailMaster.builder().
                id(MasterTableAuditTrailEmbeddedKey.builder().codMasterCode(id).masterTableName(table).lastMaker(LocalDateTime.now()).build()).
                changedValues(status).
                lastChecker((LocalDateTime) map.get("getLastCheckerDateTime")).
                lastCheckerId(String.valueOf(map.get("getLastCheckerId"))).
                lastMakerId(String.valueOf(map.get("getLastMakerId"))).
                lastChecker((LocalDateTime) map.get("getLastCheckerDateTime")).build()
        );
    }

    @Transactional
    public void save(String table, String status, Map<String, Object> ids) {

        StringBuilder sb = new StringBuilder();
        ids.forEach((k,v) -> {
            sb.append(v).append(":");
        });
        String id = sb.substring(0, sb.lastIndexOf(":"));

        repository.save(
                MasterTableAuditTrailMaster.builder().
                        id(MasterTableAuditTrailEmbeddedKey.builder().codMasterCode(id).masterTableName(table).lastMaker(LocalDateTime.now()).build()).
                        changedValues(status).
                        lastChecker(LocalDateTime.now()).
                        lastCheckerId("SYSTEM").
                        lastMakerId("SYSTEM").build()
        );
    }
}

package com.datawise.satisactual.repository;

import com.datawise.satisactual.utils.MasterTableQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class CommonMasterRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Tuple> getAllActive(String tableName) {
        String query = MasterTableQueries.GET_ALL_ACTIVE.replace("{TABLE_NAME}", tableName);
        return executeQuery(query, null);
    }

    public List<Tuple> getActive(String tableName, String idClause, Map<String, Object> params) {
        String query = MasterTableQueries.GET_ACTIVE.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        return executeQuery(query, params);
    }

    public List<Tuple> getAllNeedAuthorize(String tableName, Map<String, Object> params) {
        String query = MasterTableQueries.GET_ALL_NEED_AUTHORIZE.replace("{TABLE_NAME}", tableName);
        return executeQuery(query, params);
    }

    public List<Tuple> validateId(String tableName, String idClause, Map<String, Object> params) {
        return validateId(tableName, idClause, params, MasterTableQueries.INSERT_ID_CHECK);
    }

    public List<Tuple> validateUpdateId(String tableName, String idClause, Map<String, Object> params) {
        return validateId(tableName, idClause, params, MasterTableQueries.UPDATE_ID_CHECK);
    }

    public List<Tuple> validateDeleteId(String tableName, String idClause, Map<String, Object> params) {
        return validateId(tableName, idClause, params, MasterTableQueries.DELETE_ID_CHECK);
    }

    public List<Tuple> validateAuthorizeId(String tableName, String idClause, Map<String, Object> params) {
        return validateId(tableName, idClause, params, MasterTableQueries.AUTHORIZE_ID_CHECK);
    }

    public List<Tuple> validateId(String tableName, String idClause, Map<String, Object> params, String query) {
        query = query.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        return executeQuery(query, params);
    }

    public List<Tuple> executeQuery(String query, Map<String, Object> params) {
        Query q = entityManager.createNativeQuery(query, Tuple.class);
        if (Objects.nonNull(params) && !params.isEmpty()) params.forEach(q::setParameter);
        return q.getResultList();
    }

    @Transactional
    public void save(String tableName, String columnsList, String valuesList, Map<String, Object> params) {
        String query = MasterTableQueries.INSERT.replace("{TABLE_NAME}", tableName).replace("{COL_LIST}", columnsList).replace("{VALUE_LIST}", valuesList);
        Query q = entityManager.createNativeQuery(query);
        params.forEach(q::setParameter);
        q.executeUpdate();
    }

    @Transactional
    public void deleteAuthorize(String tableName, String idClause, Map<String, Object> params) {
        String query = MasterTableQueries.DELETE_AUTHORIZE.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        Query q = entityManager.createNativeQuery(query);
        params.forEach(q::setParameter);
        q.executeUpdate();
    }

    @Transactional
    public void authorize(String tableName, String idClause, Map<String, Object> params) {
        String query = MasterTableQueries.UPDATE_AUTHORIZE.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        Query q = entityManager.createNativeQuery(query);
        params.forEach(q::setParameter);
        q.executeUpdate();
    }

    @Transactional
    public void deleteActiveRecord(String tableName, String idClause, Map<String, Object> params) {
        String query = MasterTableQueries.DELETE_ACTIVE_RECORD.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        Query q = entityManager.createNativeQuery(query);
        params.forEach(q::setParameter);
        q.executeUpdate();
    }

    @Transactional
    public void deleteDeletedRecord(String tableName, String idClause, Map<String, Object> params) {
        String query = MasterTableQueries.DELETE_DELETED_RECORD.replace("{TABLE_NAME}", tableName).replace("{ID_CLAUSE}", idClause);
        Query q = entityManager.createNativeQuery(query);
        params.forEach(q::setParameter);
        q.executeUpdate();
    }
}

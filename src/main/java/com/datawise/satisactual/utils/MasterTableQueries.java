package com.datawise.satisactual.utils;

import com.datawise.satisactual.enums.CodRecordStatus;

public class MasterTableQueries {

    public static final String GET_ALL_ACTIVE = "SELECT * FROM {TABLE_NAME} WHERE COD_REC_STATUS = '" + CodRecordStatus.A.name() + "'";

    public static final String GET_ACTIVE = "SELECT * FROM {TABLE_NAME} WHERE COD_REC_STATUS = '" + CodRecordStatus.A.name() + "' AND {ID_CLAUSE}";

    public static final String GET_ALL_NEED_AUTHORIZE = "SELECT * FROM {TABLE_NAME} WHERE COD_REC_STATUS IN ('" + CodRecordStatus.N.name() + "', '" + CodRecordStatus.M.name() + "', '" + CodRecordStatus.X.name() + "', '" + CodRecordStatus.R.name() + "') AND TXT_LAST_MAKER_ID != :userId";

    public static final String INSERT = "INSERT INTO {TABLE_NAME} ({COL_LIST}) VALUES ({VALUE_LIST})";

    public static final String INSERT_ID_CHECK = "SELECT * FROM {TABLE_NAME} WHERE {ID_CLAUSE} AND COD_REC_STATUS IN ('" + CodRecordStatus.N.name() + "', '" + CodRecordStatus.M.name() + "', '" + CodRecordStatus.A.name() + "', '" + CodRecordStatus.R.name() + "', '" + CodRecordStatus.X.name() + "', '" + CodRecordStatus.C.name() + "')";

    public static final String UPDATE_ID_CHECK = "SELECT * FROM {TABLE_NAME} WHERE {ID_CLAUSE}";

    public static final String DELETE_ID_CHECK = "SELECT * FROM {TABLE_NAME} WHERE COD_REC_STATUS = '" + CodRecordStatus.C.name() + "' AND {ID_CLAUSE}";

    public static final String AUTHORIZE_ID_CHECK = "SELECT * FROM {TABLE_NAME} WHERE {ID_CLAUSE} AND COD_REC_STATUS IN ('" + CodRecordStatus.N.name() + "', '" + CodRecordStatus.M.name() + "', '" + CodRecordStatus.X.name() + "', '" + CodRecordStatus.R.name() + "')";

    public static final String UPDATE_AUTHORIZE = "UPDATE {TABLE_NAME} SET COD_REC_STATUS = '" + CodRecordStatus.A.name() + "', TXT_LAST_CHECKER_ID = :checkerId, DAT_LAST_CHECKER = :checkerDate WHERE {ID_CLAUSE}";

    public static final String DELETE_AUTHORIZE = "UPDATE {TABLE_NAME} SET COD_REC_STATUS = '" + CodRecordStatus.C.name() + "', TXT_LAST_CHECKER_ID = :checkerId, DAT_LAST_CHECKER = :checkerDate WHERE {ID_CLAUSE}";

    public static final String DELETE_ACTIVE_RECORD = "DELETE FROM {TABLE_NAME} WHERE COD_REC_STATUS = '" + CodRecordStatus.A.name() + "' AND {ID_CLAUSE}";

    public static final String DELETE_DELETED_RECORD = "DELETE FROM {TABLE_NAME} WHERE COD_REC_STATUS = '" + CodRecordStatus.C.name() + "' AND {ID_CLAUSE}";
}

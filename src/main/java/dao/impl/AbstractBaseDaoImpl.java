package dao.impl;

import dao.BaseDao;
import org.apache.commons.dbutils.QueryRunner;
import utils.DBUtils;

/**
 * Created by gaochen on 2018/5/20.
 */
public abstract class AbstractBaseDaoImpl<T> implements BaseDao<T> {

    public QueryRunner qr = new QueryRunner(DBUtils.getDs());

}

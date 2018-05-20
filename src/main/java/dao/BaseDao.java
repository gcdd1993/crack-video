package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public interface BaseDao<T> {
    int save(T t) throws SQLException;

    int update(T t) throws SQLException;

    T findOne(int id) throws SQLException;

    List<T> list() throws SQLException;

    int delete(int id) throws SQLException;
}

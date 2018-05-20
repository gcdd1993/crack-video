package dao.impl;

import model.VipResolver;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public class VipResolverDaoImpl extends AbstractBaseDaoImpl<VipResolver> {

    @Override
    public int save(VipResolver vipResolver) throws SQLException {
        return qr.update("INSERT INTO vip_resolver(name,url,checked,type) VALUES (?,?,?,?)",
                vipResolver.getName(),
                vipResolver.getUrl(),
                vipResolver.getChecked(),
                vipResolver.getType());
    }

    @Override
    public int update(VipResolver vipResolver) throws SQLException {
        return qr.update("UPDATE vip_resolver SET name = ?,url = ?,checked = ?,type = ? WHERE id = ?",
                vipResolver.getName(),
                vipResolver.getUrl(),
                vipResolver.getChecked(),
                vipResolver.getType(),
                vipResolver.getId());
    }

    @Override
    public VipResolver findOne(int id) throws SQLException {
        return qr.query("SELECT id,name,url,checked,type FROM vip_resolver WHERE id = ?",
                new BeanHandler<VipResolver>(VipResolver.class),
                id);
    }

    @Override
    public List<VipResolver> list() throws SQLException {
        return qr.query("SELECT id,name,url,checked,type FROM vip_resolver", new BeanListHandler<VipResolver>(VipResolver.class));
    }

    @Override
    public int delete(int id) throws SQLException {
        return qr.update("DELETE FROM vip_resolver WHERE id = ?",id);
    }
}

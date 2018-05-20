package dao.impl;

import model.Episode;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public class EpisodeDaoImpl extends AbstractBaseDaoImpl<Episode> {
    @Override
    public int save(Episode episode) throws SQLException {
        return qr.update("INSERT INTO episode(name,url,update_time) VALUES (?,?,?)",
                episode.getName(),
                episode.getUrl(),
                Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public int update(Episode episode) throws SQLException {
        return qr.update("UPDATE episode SET name = ?,url = ?,update_time = ? WHERE id = ?",
                episode.getName(),
                episode.getUrl(),
                Timestamp.valueOf(LocalDateTime.now()),
                episode.getId());
    }

    @Override
    public Episode findOne(int id) throws SQLException {
        return qr.query("SELECT id,name,url,update_time FROM episode WHERE id = ?",
                new BeanHandler<Episode>(Episode.class),
                id);
    }

    @Override
    public List<Episode> list() throws SQLException {
        return qr.query("SELECT id,name,url,update_time FROM episode", new BeanListHandler<Episode>(Episode.class));
    }

    @Override
    public int delete(int id) throws SQLException {
        return qr.update("DELETE FROM episode WHERE id = ?",id);
    }

    public Episode findByNameAndUrl(String name,String url) throws SQLException {
        return qr.query("SELECT id,name,url,update_time FROM episode WHERE name = ? AND url = ?",
                new BeanHandler<Episode>(Episode.class),
                name,
                url);
    }
}

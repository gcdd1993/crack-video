package service;

import dao.impl.EpisodeDaoImpl;
import model.Episode;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by gaochen on 2018/5/20.
 */
public class HistoryService {

    private static HistoryService historyService = new HistoryService();

    private HistoryService() {}

    public static HistoryService getInstance() {
        return historyService;
    }

    private EpisodeDaoImpl episodeDao = new EpisodeDaoImpl();

    public int save(Episode episode) {
        try {
            return episodeDao.save(episode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(Episode episode) {
        try {
            return episodeDao.update(episode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Episode findOne(int id) {
        try {
            return episodeDao.findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Episode> list() {
        try {
            return episodeDao.list();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int delete(int id) {
        try {
            return episodeDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Episode findByNameAndUrl(String name,String url) {
        try {
            return episodeDao.findByNameAndUrl(name,url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package service;

import constant.VipResolverTypeEnum;
import dao.impl.VipResolverDaoImpl;
import lombok.NonNull;
import model.VipResolver;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Created by gaochen on 2018/5/20.
 */
public class ConfigService {

    private static ConfigService configService = new ConfigService();

    public static ConfigService getInstance() {
        return configService;
    }

    private ConfigService() {
    }

    private VipResolverDaoImpl vipResolverDao = new VipResolverDaoImpl();

    public int save(VipResolver vipResolver) {
        try {
            return vipResolverDao.save(vipResolver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(VipResolver vipResolver) {
        try {
            return vipResolverDao.update(vipResolver);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public VipResolver findOne(int id) {
        try {
            return vipResolverDao.findOne(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<VipResolver> list() {
        try {
            return vipResolverDao.list();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int delete(int id) {
        try {
            return vipResolverDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 随机获取一个VIP解析器
     * @return
     */
    public Optional<VipResolver> get(@NonNull String from) {
        return this.list().stream()
                .filter(VipResolver::getChecked)
                .filter(vipResolver -> from.equals(VipResolverTypeEnum.valueOf(vipResolver.getType()).getDescription()) ||
                        VipResolverTypeEnum.valueOf(vipResolver.getType()).equals(VipResolverTypeEnum.ALL))
                .max(Comparator.comparing(VipResolver::getType));
    }

}

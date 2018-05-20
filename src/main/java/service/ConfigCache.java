package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import constant.VIPResolverTypeEnum;
import lombok.NonNull;
import model.VipResolver;
import utils.GUIUtil;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by gaochen on 2018/5/16.
 * 配置文件服务
 */
public class ConfigCache {

    private static ConfigCache configCache = new ConfigCache();

    private ConfigCache() {
    }

    public static ConfigCache getInstance() {
        return configCache;
    }

    private static final String CONFIG = System.getProperty("user.dir") + "/config/vipResolver.json";

    private static List<VipResolver> vipResolverList;

    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        vipResolverList = mapper.readValue(new FileReader(CONFIG),
                mapper.getTypeFactory().constructCollectionType(List.class, VipResolver.class));
        if(vipResolverList == null || vipResolverList.isEmpty()) {
            GUIUtil.alert("没有找到VIP解析器!");
        }
        System.out.println("vipResolverList : " + vipResolverList);
    }

    public List<VipResolver> listVipResolver() {
        return vipResolverList.stream().sorted(Comparator.comparing(VipResolver::getName)).collect(Collectors.toList());
    }

    /**
     * 刷新VIP解析器
     * @param vipResolvers
     */
    public void refresh(List<VipResolver> vipResolvers) {
        vipResolverList.clear();
        vipResolverList.addAll(vipResolvers);
        try {
            save();
        } catch (IOException e) {
            System.out.println("save error " + e.getMessage());
        }
    }

    /**
     * 保存VIP解析器到文件
     * @throws IOException
     */
    private void save() throws IOException {
        System.out.println("refresh config file");
        ObjectMapper mapper = new ObjectMapper();
        String vips = mapper.writeValueAsString(vipResolverList);
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(CONFIG))) {
            bw.write(vips,0,vips.length());
            bw.flush();
        }
    }

    /**
     * 随机获取一个VIP解析器
     * @return
     */
    public Optional<VipResolver> get(@NonNull String from) {
        return vipResolverList.stream()
                .filter(VipResolver::isChecked)
                .filter(vipResolver -> from.equals(vipResolver.getType().getDescription()) ||
                        vipResolver.getType().equals(VIPResolverTypeEnum.ALL))
                .max(Comparator.comparing(VipResolver::getType));
    }
}

package utils;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;

/**
 * Created by gaochen on 2018/5/20.
 */
public class DBUtils {

    private static BasicDataSource ds; //数据库连接池

    public static BasicDataSource getDs() {
        return ds;
    }
    static {
        try {
            String drivername = "org.sqlite.JDBC";
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/config/config.db";

            //初始化连接池
            ds = new BasicDataSource();
            ds.setDriverClassName(drivername);
            ds.setUrl(url);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static Connection getConnection() throws Exception {
        /*
         * 连接池提供了获取连接的方法
         * Connection getConnection()
         * 该方法会将连接池中的空闲连接返回，若当前连接池中没有空闲连接，那么该方法会阻塞，阻塞时间由maxwait决定，
         * 在阻塞期间若有空闲连接可用会立刻返回该连接，若超时会抛出异常
         */
        try {
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

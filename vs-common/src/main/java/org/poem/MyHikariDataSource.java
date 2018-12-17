package org.poem;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 第一个HikariCP演示例子
 *
 * @author Cheng
 */
public class MyHikariDataSource {

    private static final Logger logger = LoggerFactory.getLogger(MyHikariDataSource.class);

    private static String driverClassName = "org.postgresql.Driver";
    private static String url = "jdbc:postgresql://192.168.101.62:5432/id_etl?searchpath=id_xcff&serverTimezone=Hongkong";
    private static String username = "postgres";
    private static String password = "postgres";

    //连接池
    private static HikariDataSource dataSource;

    static {
        init();
    }

    private static void init() {

        //连接池配置信息
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setConnectionTimeout(Integer.MAX_VALUE);
        logger.info("MyHikariDataSource " + url);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        //链接池初始化
        setDataSource(new HikariDataSource(config));
    }

    /**
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        try {
            con = getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * @return
     */
    private static HikariDataSource getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource
     */
    private static void setDataSource(HikariDataSource dataSource) {
        MyHikariDataSource.dataSource = dataSource;
    }

    /**
     *
     * @param con
     * @param statement
     * @param resultSet
     */
    public static void closeConnection(Connection con, Statement statement, ResultSet resultSet) {
        if (null != resultSet) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (null != con) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}

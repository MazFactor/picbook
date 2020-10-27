package com.jinghuan.common.util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.SQLException;

/**
 * Druid工具类
 *
 * @author WRQ
 * @date 2019/4/28
 * @since 1.0.0
 */
public class DruidUtil {

    /**
     * 初始化数据源
     *
     * @param dataSource 要初始化的数据源
     * @param url 数据库连接
     * @param user 用户名
     * @param pwd 密码
     */
    public static void initDataSource(DruidDataSource dataSource, String url, String user, String pwd) {
        try {
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(pwd);
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(3);
            dataSource.setMaxActive(10);
            dataSource.setBreakAfterAcquireFailure(true);
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

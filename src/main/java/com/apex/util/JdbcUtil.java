package com.apex.util;

import java.sql.Connection;
import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSource;

/**
 *
 * @author jsh
 *
 */
public class JdbcUtil {

	private static DruidDataSource dataSource = null;

    static {
        try {
            PropertyUtil.appendProps("jdbc.properties");
            String driver = PropertyUtil.getProperty("jdbc.driver");
            String url = PropertyUtil.getProperty("jdbc.url");
            String user = PropertyUtil.getProperty("jdbc.username");
            String password = PropertyUtil.getProperty("jdbc.password");

            dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setInitialSize(5);
            dataSource.setMinIdle(5);
            dataSource.setMaxActive(20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
	public static DataSource getDataSource(){
		return dataSource;
	}

    public static synchronized Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}	

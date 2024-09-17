package com.rwto.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author renmw
 * @create 2024/9/17 22:35
 **/
public interface DataSourceFactory {
    void setProperties(Properties props);

    DataSource getDataSource();
}

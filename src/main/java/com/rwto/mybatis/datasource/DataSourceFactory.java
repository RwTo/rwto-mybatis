package com.rwto.mybatis.datasource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author renmw
 * @since 2025/8/21 14:23
 **/
public interface DataSourceFactory {

    /**
     * 设置属性
     * @param props
     */
    void setProperties(Properties props);

    /**
     * 获取数据源
     * @return
     */
    DataSource getDataSource();
}

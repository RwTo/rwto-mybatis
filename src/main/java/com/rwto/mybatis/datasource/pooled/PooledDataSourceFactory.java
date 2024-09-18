package com.rwto.mybatis.datasource.pooled;

import com.rwto.mybatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;

/**
 * @author renmw
 * @create 2024/9/18 13:38
 **/
public class PooledDataSourceFactory extends UnpooledDataSourceFactory {
    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }

}

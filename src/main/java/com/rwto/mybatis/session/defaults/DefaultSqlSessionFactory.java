package com.rwto.mybatis.session.defaults;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.session.SqlSession;
import com.rwto.mybatis.session.SqlSessionFactory;

/**
 * @author renmw
 * @create 2024/9/17 1:51
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }

}

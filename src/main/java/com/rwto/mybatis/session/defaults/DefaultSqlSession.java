package com.rwto.mybatis.session.defaults;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.session.SqlSession;

/**
 * @author renmw
 * @create 2024/9/17 1:50
 **/
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSession(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你的操作被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        return (T) ("你的操作被代理了！" + "方法：" + statement + " 入参：" + parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return mapperRegistry.getMapper(type, this);
    }

}

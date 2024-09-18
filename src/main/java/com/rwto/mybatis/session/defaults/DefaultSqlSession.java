package com.rwto.mybatis.session.defaults;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.executor.Executor;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author renmw
 * @create 2024/9/17 1:50
 **/
public class DefaultSqlSession implements SqlSession {

    /**
     * 映射器注册机
     */
    private final Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <T> T selectOne(String statement) {
        return (T) ("你的操作被代理了！" + statement);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            List<T> objList = executor.query(mappedStatement, parameter, Executor.NO_RESULT_HANDLER, mappedStatement.getBoundSql());

            return objList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return configuration.getMapper(type, this);
    }

    @Override
    public Configuration getConfiguration() {
        return this.configuration;
    }

}

package com.rwto.mybatis.builder;

import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.ParameterMapping;
import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.session.Configuration;

import java.util.List;

/**
 * @author renmw
 * @create 2024/9/19 9:37
 **/
public class StaticSqlSource implements SqlSource {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Configuration configuration;

    public StaticSqlSource(Configuration configuration, String sql) {
        this(configuration, sql, null);
    }

    public StaticSqlSource(Configuration configuration, String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.configuration = configuration;
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return new BoundSql(configuration, sql, parameterMappings, parameterObject);
    }
}

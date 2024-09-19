package com.rwto.mybatis.scripting.defaults;

import com.rwto.mybatis.builder.SqlSourceBuilder;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.scripting.xmltags.DynamicContext;
import com.rwto.mybatis.scripting.xmltags.SqlNode;
import com.rwto.mybatis.session.Configuration;

import java.util.HashMap;

/**
 * @author renmw
 * @create 2024/9/19 9:30
 **/
public class RawSqlSource implements SqlSource {
    private final SqlSource sqlSource;

    public RawSqlSource(Configuration configuration, SqlNode rootSqlNode, Class<?> parameterType) {
        this(configuration, getSql(configuration, rootSqlNode), parameterType);
    }

    public RawSqlSource(Configuration configuration, String sql, Class<?> parameterType) {
        SqlSourceBuilder sqlSourceParser = new SqlSourceBuilder(configuration);
        Class<?> clazz = parameterType == null ? Object.class : parameterType;
        sqlSource = sqlSourceParser.parse(sql, clazz, new HashMap<>());
    }

    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        return sqlSource.getBoundSql(parameterObject);
    }

    private static String getSql(Configuration configuration, SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(configuration, null);
        rootSqlNode.apply(context);
        return context.getSql();
    }
}

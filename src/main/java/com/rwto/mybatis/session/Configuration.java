package com.rwto.mybatis.session;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.datasource.druid.DruidDataSourceFactory;
import com.rwto.mybatis.datasource.executor.Executor;
import com.rwto.mybatis.datasource.executor.SimpleExecutor;
import com.rwto.mybatis.datasource.executor.resultset.DefaultResultSetHandler;
import com.rwto.mybatis.datasource.executor.resultset.ResultSetHandler;
import com.rwto.mybatis.datasource.executor.statement.PreparedStatementHandler;
import com.rwto.mybatis.datasource.executor.statement.StatementHandler;
import com.rwto.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.rwto.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.transaction.Transaction;
import com.rwto.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.rwto.mybatis.type.TypeAliasRegistry;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author renmw
 * @since 2025/8/13 0:09
 **/
public class Configuration {

    /**
     * 环境信息
     */
    @Setter
    @Getter
    protected Environment environment;

    /**
     * 映射注册机
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry();

    /**
     * 映射的语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * 类型别名注册机
     */
    @Getter
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);

        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
    }


    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }


    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }


    /**
     * 创建结果集处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }
}

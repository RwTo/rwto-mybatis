package com.rwto.mybatis.session;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.datasource.druid.DruidDataSourceFactory;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.session.defaults.DefaultSqlSession;
import com.rwto.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.rwto.mybatis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author renmw
 * @create 2024/9/17 16:53
 **/
public class Configuration {

    //环境
    protected Environment environment;
    /**
     * 存放mapper的代理工厂
     */
    protected final MapperRegistry mapperRegistry = new MapperRegistry();
    /**
     * 存放 sql语句 namespace.id  -> MappedStatement
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    // 类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();


    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
    }


    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type,sqlSession);
    }

    public void addMappedStatement(MappedStatement mappedStatement) {
        mappedStatements.put(mappedStatement.getId(),mappedStatement);
    }

    public void addMapper(Class<?> classForName) {
        mapperRegistry.addMapper(classForName);
    }

    public MappedStatement getMappedStatement(String statementName) {
        return mappedStatements.get(statementName);
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return this.typeAliasRegistry;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }
}

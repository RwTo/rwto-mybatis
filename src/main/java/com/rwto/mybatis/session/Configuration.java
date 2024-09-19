package com.rwto.mybatis.session;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.datasource.druid.DruidDataSourceFactory;
import com.rwto.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.rwto.mybatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.rwto.mybatis.executor.Executor;
import com.rwto.mybatis.executor.SimpleExecutor;
import com.rwto.mybatis.executor.resultset.DefaultResultSetHandler;
import com.rwto.mybatis.executor.resultset.ResultSetHandler;
import com.rwto.mybatis.executor.statement.PreparedStatementHandler;
import com.rwto.mybatis.executor.statement.StatementHandler;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.reflection.MetaObject;
import com.rwto.mybatis.reflection.factory.DefaultObjectFactory;
import com.rwto.mybatis.reflection.factory.ObjectFactory;
import com.rwto.mybatis.reflection.wrapper.DefaultObjectWrapperFactory;
import com.rwto.mybatis.reflection.wrapper.ObjectWrapperFactory;
import com.rwto.mybatis.scripting.LanguageDriverRegistry;
import com.rwto.mybatis.scripting.xmltags.XMLLanguageDriver;
import com.rwto.mybatis.session.defaults.DefaultSqlSession;
import com.rwto.mybatis.transaction.Transaction;
import com.rwto.mybatis.transaction.jdbc.JdbcTransactionFactory;
import com.rwto.mybatis.type.TypeAliasRegistry;
import com.rwto.mybatis.type.TypeHandlerRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    protected final LanguageDriverRegistry languageRegistry = new LanguageDriverRegistry();

    // 类型处理器注册机
    protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    // 对象工厂和对象包装器工厂
    protected ObjectFactory objectFactory = new DefaultObjectFactory();
    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();

    protected final Set<String> loadedResources = new HashSet<>();

    protected String databaseId;


    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);

        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);

        languageRegistry.setDefaultDriverClass(XMLLanguageDriver.class);
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

    // 创建元对象
    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory);
    }

    // 类型处理器注册机
    public TypeHandlerRegistry getTypeHandlerRegistry() {
        return typeHandlerRegistry;
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public LanguageDriverRegistry getLanguageRegistry() {
        return languageRegistry;
    }

    public Object getDatabaseId() {
        return databaseId;
    }
}

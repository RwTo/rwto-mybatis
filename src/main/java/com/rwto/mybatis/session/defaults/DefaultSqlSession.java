package com.rwto.mybatis.session.defaults;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author renmw
 * @since 2025/8/12 23:55
 **/
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public <T> T selectOne(String statement, Object parameter) {
        try {
            MappedStatement mappedStatement = configuration.getMappedStatement(statement);
            Environment environment = configuration.getEnvironment();

            Connection connection = environment.getDataSource().getConnection();

            BoundSql boundSql = mappedStatement.getBoundSql();

            PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSql());

            preparedStatement.setLong(1, Long.parseLong(((Object[]) parameter)[0].toString()));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<T> objList = resultSet2Obj(resultSet, Class.forName(boundSql.getResultType()));

            return objList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private <T> List<T> resultSet2Obj(ResultSet resultSet, Class<?> clazz) {
        List<T> list = new ArrayList<>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 返回的字段数量
            int columnCount = metaData.getColumnCount();
            // 每次遍历行值
            while (resultSet.next()) {
                T obj = (T) clazz.newInstance();
                for (int i = 1; i <= columnCount; i++) {
                    Object value = resultSet.getObject(i);
                    String columnName = metaData.getColumnName(i);
                    //拼接set方法
                    String setMethod = "set" + columnName.substring(0, 1).toUpperCase() + columnName.substring(1);
                    Method method;
                    if (value instanceof Timestamp) {
                        // 反射获取方法
                        method = clazz.getMethod(setMethod, Date.class);
                    } else {
                        method = clazz.getMethod(setMethod, value.getClass());
                    }
                    method.invoke(obj, value);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public <T> T getMapper(Class<T> mapperClass) {
        return configuration.getMapper(mapperClass,this);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}

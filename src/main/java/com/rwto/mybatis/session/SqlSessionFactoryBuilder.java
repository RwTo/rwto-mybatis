package com.rwto.mybatis.session;

import com.rwto.mybatis.builder.xml.XMLConfigBuilder;
import com.rwto.mybatis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * @author renmw
 * @create 2024/9/17 14:56
 **/
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}

package com.rwto.mybatis.scripting;

import com.rwto.mybatis.executor.parameter.ParameterHandler;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author renmw
 * @create 2024/9/19 9:29
 **/
public interface LanguageDriver {

    /**
     * 创建SQL源码(mapper xml方式)
     */
    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

    /**
     * 创建参数处理器
     */
    ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);


}

package com.rwto.mybatis.scripting.xmltags;

import com.rwto.mybatis.executor.parameter.ParameterHandler;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.scripting.LanguageDriver;
import com.rwto.mybatis.scripting.defaults.DefaultParameterHandler;
import com.rwto.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author renmw
 * @create 2024/9/19 9:34
 **/
public class XMLLanguageDriver implements LanguageDriver {
    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
    }
}

package com.rwto.mybatis.scripting.xmltags;

import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.scripting.LanguageDriver;
import com.rwto.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author renmw
 * @since 2025/1/26 19:40
 **/
public class XMLLanguageDriver implements LanguageDriver {

    @Override
    public SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType) {
        // 用XML脚本构建器解析
        XMLScriptBuilder builder = new XMLScriptBuilder(configuration, script, parameterType);
        return builder.parseScriptNode();
    }

}

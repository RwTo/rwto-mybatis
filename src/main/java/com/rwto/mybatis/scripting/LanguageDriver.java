package com.rwto.mybatis.scripting;

import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author renmw
 * @since 2025/1/26 19:40
 **/
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

}

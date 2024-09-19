package com.rwto.mybatis.scripting;

import com.rwto.mybatis.mapping.SqlSource;
import com.rwto.mybatis.session.Configuration;
import org.dom4j.Element;

/**
 * @author renmw
 * @create 2024/9/19 9:29
 **/
public interface LanguageDriver {

    SqlSource createSqlSource(Configuration configuration, Element script, Class<?> parameterType);

}

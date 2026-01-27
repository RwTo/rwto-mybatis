package com.rwto.mybatis.mapping;

/**
 * @author renmw
 * @since 2026/1/26 17:36
 **/
public interface SqlSource {

    BoundSql getBoundSql(Object parameterObject);

}

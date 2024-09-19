package com.rwto.mybatis.mapping;

/**
 * @author renmw
 * @create 2024/9/19 9:26
 **/
public interface SqlSource {
    BoundSql getBoundSql(Object parameterObject);
}

package com.rwto.mybatis.session;

/**
 * @author renmw
 * @since 2025/8/12 14:54
 **/
public interface SqlSession {

    /**
     * 执行selectOne
     * @param statementName
     * @return
     * @param <T>
     */
    <T> T selectOne(String statementName, Object parameter);


    /**
     * 获取Mapper
     * @param mapperClass
     * @return
     * @param <T>
     */
    <T> T getMapper(Class<T> mapperClass);


    /**
     * 获取全局配置
     * @return
     */
    Configuration getConfiguration();
}

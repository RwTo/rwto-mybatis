package com.rwto.mybatis.session;

/**
 * @author renmw
 * @since 2025/8/12 14:55
 **/
public interface SqlSessionFactory {

    /**
     * 打开有一个session
     * @return
     */
    SqlSession openSession();
}

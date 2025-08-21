package com.rwto.mybatis.test;

import com.rwto.mybatis.io.Resources;
import com.rwto.mybatis.session.SqlSession;
import com.rwto.mybatis.session.SqlSessionFactory;
import com.rwto.mybatis.session.SqlSessionFactoryBuilder;
import com.rwto.mybatis.test.dao.IUserDao;
import com.rwto.mybatis.test.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * @author renmw
 * @since 2025/8/13 0:57
 **/
@Slf4j
public class ApiTest {

    @Test
    public void testSqlSessionFactory() throws IOException {
        // 1. 从SqlSessionFactory中获取SqlSession
        Reader reader = Resources.getResourceAsReader("mybatis-config-datasource.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 2. 获取映射器对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        // 3. 测试验证
        User res = userDao.selectUserById("1");
        log.info("测试结果：{}", res);
    }
}

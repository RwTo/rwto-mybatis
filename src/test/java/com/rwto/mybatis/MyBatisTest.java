package com.rwto.mybatis;

import com.rwto.mybatis.binding.MapperProxyFactory;
import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.builder.xml.XMLConfigBuilder;
import com.rwto.mybatis.dao.UserDao;
import com.rwto.mybatis.datasource.pooled.PooledDataSource;
import com.rwto.mybatis.datasource.pooled.PooledDataSourceFactory;
import com.rwto.mybatis.datasource.unpooled.UnpooledDataSource;
import com.rwto.mybatis.io.Resources;
import com.rwto.mybatis.session.SqlSession;
import com.rwto.mybatis.session.SqlSessionFactory;
import com.rwto.mybatis.session.SqlSessionFactoryBuilder;
import com.rwto.mybatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author renmw
 * @create 2024/9/16 23:23
 **/
public class MyBatisTest {

    /**
     * jdk动态代理，构建真实mapper对象，模拟执行sql
     */
    @Test
    public void test01(){
        // 模拟解析xml创建sqlSession
//        Map<String, String> sqlSession = new HashMap<>();
//        sqlSession.put("com.rwto.mybatis.test.dao.UserDao.getUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
//
//        MapperProxyFactory<UserDao> factory = new MapperProxyFactory<>(UserDao.class);
//        UserDao userDao = factory.newInstance(sqlSession);
//        System.out.println(userDao.getUserName("123"));
    }


    /**
     * 构建MapperRegistry 统一管理MapperProxyFactory, 通过扫描包创建MapperRegistry
     * 创建SqlSession对象，用于执行相关sql
     */
    @Test
    public void test02(){
//        // 包扫描，生成mapperRegistry
//        MapperRegistry mapperRegistry = new MapperRegistry();
//        mapperRegistry.addMappers("com.rwto.mybatis.dao");
//
//        //构建sqlSession
//        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(mapperRegistry);
//        SqlSession sqlSession = defaultSqlSessionFactory.openSession();
//
//
//        UserDao userDao = sqlSession.getMapper(UserDao.class);
//
//        System.out.println(userDao.getUserName("123"));
    }

    /**
     * 增加配置文件解析，通过配置文件，配置包扫描
     * 增加连接池，执行sql
     */
    @Test
    public void test03() throws IOException {
        //获取 mybatis-config.xml 字符流
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");

        //解析配置文件，获取包扫描路径，构建MapperRegistry 进而构建 SqlSessionFactory
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession sqlSession = sessionFactory.openSession();

        UserDao userDao = sqlSession.getMapper(UserDao.class);

        System.out.println(userDao.getUserInfoById("1"));
    }


    /**
     * 验证连接池
     * @throws IOException
     */
    @Test
    public void test04() throws IOException, InterruptedException, SQLException {
        PooledDataSource pooledDataSource = new PooledDataSource();
        pooledDataSource.setDriver("com.mysql.cj.jdbc.Driver");
        pooledDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/boot_study?useUnicode=true");
        pooledDataSource.setUsername("local");
        pooledDataSource.setPassword("1234");

        // 持续获得链接
        while (true) {
            /*数据资源连接池是一个一个创建的*/
            Connection connection = pooledDataSource.getConnection();
            /*
            * 这里实际调用的 PooledConnection中的 realConnection 的toString()方法
            * realConnection 实际没有改变，所以每次打印的值相同
            * */
            System.out.println(connection);
            Thread.sleep(1000);
            // 注释掉/不注释掉测试
            //connection.close();
        }
    }

    /**
     * 验证同一个连接，执行两个sql, 会按照顺序 串行执行
     * @throws IOException
     */
    @Test
    public void test05() throws IOException, InterruptedException, SQLException {
        UnpooledDataSource unpooledDataSource = new UnpooledDataSource();
        unpooledDataSource.setDriver("com.mysql.cj.jdbc.Driver");
        unpooledDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/boot_study?useUnicode=true");
        unpooledDataSource.setUsername("local");
        unpooledDataSource.setPassword("1234");

        Connection connection = unpooledDataSource.getConnection();

        new Thread(()->{
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(" update t_user set user_name = '11' where id = 11");
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        new Thread(()->{
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(" update t_user set user_name = 'rmw' where id = 1");
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }).start();

        while (true){

        }
    }


    /**
     *
     * @throws Exception
     */
    @Test
    public void test06() throws Exception {

    }
}

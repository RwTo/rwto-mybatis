package com.rwto.mybatis.binding;

import com.rwto.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author renmw
 * @since 2025/8/11 22:35
 **/
public class MapperProxy<T> implements InvocationHandler {

    /**
     * sqlSession信息
     */
    private final SqlSession sqlSession;
    /**
     * mapper接口，用于获取执行的那个接口
     */
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果声明此方法的类是 Object ，不进行代理，例如 equals，toString等方法
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            //TODO 是否需要缓存MapperMethod？
            MapperMethod mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            return mapperMethod.execute(sqlSession, args);
        }
    }
}

package com.rwto.mybatis.binding;

import com.rwto.mybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author renmw
 * @create 2024/9/16 23:36
 **/
public class MapperProxy<T> implements InvocationHandler {

    private final Class<T> mapperInterface;
    private final SqlSession sqlSession;

    public MapperProxy(Class<T> mapperInterface, SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Object 定义的方法，执行原方法，不增强
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }
        return "代理执行sql" + sqlSession.selectOne(method.getName(),args);
    }
}

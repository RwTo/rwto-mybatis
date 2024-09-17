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
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(Class<T> mapperInterface, SqlSession sqlSession, Map<Method, MapperMethod> methodCache) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
        this.methodCache = methodCache;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Object 定义的方法，执行原方法，不增强
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }
        //从缓存中取,不用每次都去new这个对象---也可以用单例
        final MapperMethod mapperMethod = cachedMapperMethod(method);
        return mapperMethod.execute(sqlSession, args);
    }

    /**
     * 去缓存中找MapperMethod
     */
    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            //找不到才去new
            mapperMethod = new MapperMethod(mapperInterface, method, sqlSession.getConfiguration());
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }
}

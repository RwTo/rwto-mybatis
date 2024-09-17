package com.rwto.mybatis.binding;

import com.rwto.mybatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author renmw
 * @create 2024/9/16 23:31
 * 统一的Mapper代理工厂
 **/
public class MapperProxyFactory<T> {

    private final Class<T> mapperInterface;

    private Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<Method, MapperMethod>();

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Map<Method, MapperMethod> getMethodCache() {
        return methodCache;
    }

    //忽略编译器 因泛型擦除的告警
    @SuppressWarnings("unchecked")
    public T newInstance(SqlSession sqlSession){
        MapperProxy<T> mapperProxy = new MapperProxy<>(this.mapperInterface, sqlSession, methodCache);
        //这里会出现告警，因为经过泛型擦除后，T为Object，编译器不知道能否强转
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
    }
}

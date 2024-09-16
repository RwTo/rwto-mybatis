package com.rwto.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import com.rwto.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author renmw
 * @create 2024/9/17 0:26
 **/
public class MapperRegistry {

    private Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession){
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }

        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }

    public void addMappers(String packageName){
        Set<Class<?>> classes = ClassScanner.scanPackage();
        for (Class<?> clz : classes) {
            addMapper(clz);
        }
    }

    private void addMapper(Class<?> type) {
        if(type.isInterface()){
            if (hasMapper(type)) {
                // 如果重复添加了，报错
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }
            // 注册映射器代理工厂
            knownMappers.put(type, new MapperProxyFactory<>(type));
        }
    }

    private boolean hasMapper(Class<?> type) {
        return knownMappers.containsKey(type);
    }
}

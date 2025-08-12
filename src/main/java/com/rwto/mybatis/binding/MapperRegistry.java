package com.rwto.mybatis.binding;

import cn.hutool.core.lang.ClassScanner;
import com.rwto.mybatis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author renmw
 * @since 2025/8/12 14:01
 **/
public class MapperRegistry {

    /**
     * 已知的mapper
     */
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();


    /**
     * 获取mapper的代理类
     * @param type
     * @return
     * @param <T>
     */
    @SuppressWarnings("uncheck")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);

        if(null == mapperProxyFactory) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }

        return (T) mapperProxyFactory.newInstance(sqlSession);

    }


    /**
     * 添加Mapper
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type){
        if(type.isInterface()) {
            if(hashMapper(type)){
                throw new RuntimeException("Type " + type + " is already known to the MapperRegistry.");
            }

            knownMappers.put(type, new MapperProxyFactory<T>(type));
        }
    }

    /**
     * 判断是否已存在Mapper
     * @param type
     * @return
     * @param <T>
     */
    private <T> boolean hashMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }


    /**
     * 批量添加Mapper
     * @param packageName
     */
    public void addMappers(String packageName){
        Set<Class<?>> mapperSet = ClassScanner.scanPackage(packageName);
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }
}

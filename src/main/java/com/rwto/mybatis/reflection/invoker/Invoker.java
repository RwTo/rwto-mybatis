package com.rwto.mybatis.reflection.invoker;

/**
 * @author renmw
 * @create 2024/9/18 21:03
 **/
public interface Invoker {

    Object invoke(Object target, Object[] args) throws Exception;

    Class<?> getType();

}

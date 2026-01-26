package com.rwto.mybatis.reflection.invoker;

/**
 * @author renmw
 * @since 2025/12/19 16:25
 **/
public interface Invoker {

    Object invoke(Object target, Object[] args) throws Exception;

    Class<?> getType();

}

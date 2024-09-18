package com.rwto.mybatis.reflection.wrapper;

import com.rwto.mybatis.reflection.MetaObject;

/**
 * @author renmw
 * @create 2024/9/18 21:03
 **/
public interface ObjectWrapperFactory {

    /**
     * 判断有没有包装器
     */
    boolean hasWrapperFor(Object object);

    /**
     * 得到包装器
     */
    ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);

}

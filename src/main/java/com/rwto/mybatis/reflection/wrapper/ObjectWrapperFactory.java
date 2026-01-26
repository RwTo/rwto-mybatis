package com.rwto.mybatis.reflection.wrapper;

import com.rwto.mybatis.reflection.MetaObject;

/**
 * @author renmw
 * @since 2025/12/19 16:47
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

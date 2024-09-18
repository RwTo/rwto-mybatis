package com.rwto.mybatis.reflection.wrapper;

import com.rwto.mybatis.reflection.MetaObject;

/**
 * @author renmw
 * @create 2024/9/18 21:03
 **/
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        throw new RuntimeException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }

}

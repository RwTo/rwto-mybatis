package com.rwto.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author renmw
 * @create 2024/9/18 21:03
 **/
public class SetFieldInvoker implements Invoker {

    private Field field;

    public SetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        field.set(target, args[0]);
        return null;
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

}
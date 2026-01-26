package com.rwto.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author renmw
 * @since 2025/12/19 16:36
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

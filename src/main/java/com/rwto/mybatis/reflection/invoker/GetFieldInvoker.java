package com.rwto.mybatis.reflection.invoker;

import java.lang.reflect.Field;

/**
 * @author renmw
 * @create 2024/9/18 21:03
 **/
public class GetFieldInvoker implements Invoker {

    private Field field;

    public GetFieldInvoker(Field field) {
        this.field = field;
    }

    @Override
    public Object invoke(Object target, Object[] args) throws Exception {
        return field.get(target);
    }

    @Override
    public Class<?> getType() {
        return field.getType();
    }

}

package com.rwto.mybatis.builder;


import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.type.TypeAliasRegistry;
import com.rwto.mybatis.type.TypeHandlerRegistry;

/**
 * @author renmw
 * @create 2024/9/17 16:50
 **/
public abstract class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;
    protected final TypeHandlerRegistry typeHandlerRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}

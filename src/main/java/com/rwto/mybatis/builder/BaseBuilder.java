package com.rwto.mybatis.builder;


import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.type.TypeAliasRegistry;

/**
 * @author renmw
 * @create 2024/9/17 16:50
 **/
public abstract class BaseBuilder {
    protected final Configuration configuration;
    protected final TypeAliasRegistry typeAliasRegistry;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

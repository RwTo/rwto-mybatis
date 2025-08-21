package com.rwto.mybatis.builder;

import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.type.TypeAliasRegistry;

/**
 * @author renmw
 * @since 2025/8/13 0:07
 **/
public class BaseBuilder {
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

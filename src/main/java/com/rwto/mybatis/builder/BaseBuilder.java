package com.rwto.mybatis.builder;

import com.rwto.mybatis.session.Configuration;

/**
 * @author renmw
 * @since 2025/8/13 0:07
 **/
public class BaseBuilder {
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

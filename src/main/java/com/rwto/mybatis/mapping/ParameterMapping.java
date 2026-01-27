package com.rwto.mybatis.mapping;

import cn.hutool.db.meta.JdbcType;
import com.rwto.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author renmw
 * @since 2026/1/26 17:36
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterMapping {

    private Configuration configuration;

    // property
    private String property;
    // javaType = int
    private Class<?> javaType = Object.class;
    // jdbcType=NUMERIC
    private JdbcType jdbcType;


    public Configuration getConfiguration() {
        return configuration;
    }

    public String getProperty() {
        return property;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

}

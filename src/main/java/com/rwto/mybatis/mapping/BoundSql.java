package com.rwto.mybatis.mapping;

import com.rwto.mybatis.reflection.MetaObject;
import com.rwto.mybatis.session.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author renmw
 * @since 2025/8/21 14:40
 **/
@Getter
public class BoundSql {

    private String sql;
    private List<ParameterMapping> parameterMappings;
    private Object parameterObject;
    private Map<String, Object> additionalParameters;
    private MetaObject metaParameters;

    public BoundSql(Configuration configuration, String sql, List<ParameterMapping> parameterMappings, Object parameterObject) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
        this.parameterObject = parameterObject;
        this.additionalParameters = new HashMap<>();
        this.metaParameters = configuration.newMetaObject(additionalParameters);
    }

}

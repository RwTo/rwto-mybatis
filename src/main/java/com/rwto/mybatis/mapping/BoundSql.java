package com.rwto.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author renmw
 * @since 2025/8/21 14:40
 **/
@Getter
@AllArgsConstructor
public class BoundSql {

    private String sql;
    private Map<Integer, String> parameterMappings;
    private String parameterType;
    private String resultType;

}

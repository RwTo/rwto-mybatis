package com.rwto.mybatis.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author renmw
 * @create 2024/9/17 23:03
 **/
@Data
@AllArgsConstructor
public class BoundSql {
    private String sql;
    private Map<Integer, String> parameterMappings;
    private String parameterType;
    private String resultType;
}

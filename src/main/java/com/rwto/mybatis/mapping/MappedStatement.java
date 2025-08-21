package com.rwto.mybatis.mapping;

import com.rwto.mybatis.session.Configuration;
import lombok.*;

import java.util.Map;

/**
 * @author renmw
 * @since 2025/8/13 0:27
 * 一条sql语句的声明信息
 **/
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MappedStatement {
    private Configuration configuration;
    private String id;
    private SqlCommandType sqlCommandType;

    private BoundSql boundSql;
}

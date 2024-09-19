package com.rwto.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author renmw
 * @create 2024/9/19 9:41
 **/
public interface TypeHandler<T> {
    /**
     * 设置参数
     */
    void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException;

}

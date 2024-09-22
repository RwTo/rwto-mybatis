package com.rwto.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author renmw
 * @create 2024/9/19 16:37
 **/
public class LongTypeHandler extends BaseTypeHandler<Long>{

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter);
    }
}

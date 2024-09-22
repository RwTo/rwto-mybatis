package com.rwto.mybatis.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author renmw
 * @create 2024/9/19 16:37
 **/
public class StringTypeHandler extends BaseTypeHandler<String>{

    @Override
    protected void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }
}

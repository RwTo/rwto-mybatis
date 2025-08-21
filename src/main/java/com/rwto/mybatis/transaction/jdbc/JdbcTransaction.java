package com.rwto.mybatis.transaction.jdbc;

import com.rwto.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author renmw
 * @since 2025/8/21 14:15
 **/
public class JdbcTransaction implements Transaction {
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public void commit() throws SQLException {

    }

    @Override
    public void rollback() throws SQLException {

    }

    @Override
    public void close() throws SQLException {

    }
}

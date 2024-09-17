package com.rwto.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author renmw
 * @create 2024/9/17 22:18
 **/
public interface Transaction {
    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}

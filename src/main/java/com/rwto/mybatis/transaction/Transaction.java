package com.rwto.mybatis.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author renmw
 * @since 2025/8/21 14:14
 **/
public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;
}

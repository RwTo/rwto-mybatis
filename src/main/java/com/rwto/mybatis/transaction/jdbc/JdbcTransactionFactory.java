package com.rwto.mybatis.transaction.jdbc;

import com.rwto.mybatis.session.TransactionIsolationLevel;
import com.rwto.mybatis.transaction.Transaction;
import com.rwto.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author renmw
 * @since 2025/8/21 14:14
 **/
public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(Connection conn) {
        return null;
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return null;
    }
}

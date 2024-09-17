package com.rwto.mybatis.transaction.jdbc;

import com.rwto.mybatis.session.TransactionIsolationLevel;
import com.rwto.mybatis.transaction.Transaction;
import com.rwto.mybatis.transaction.TransactionFactory;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author renmw
 * @create 2024/9/17 22:23
 **/
public class JdbcTransactionFactory implements TransactionFactory {
    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        return new JdbcTransaction(dataSource, level, autoCommit);
    }
}

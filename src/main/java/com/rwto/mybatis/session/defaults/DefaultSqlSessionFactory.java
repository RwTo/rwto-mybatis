package com.rwto.mybatis.session.defaults;

import com.rwto.mybatis.binding.MapperRegistry;
import com.rwto.mybatis.executor.Executor;
import com.rwto.mybatis.mapping.Environment;
import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.session.SqlSession;
import com.rwto.mybatis.session.SqlSessionFactory;
import com.rwto.mybatis.session.TransactionIsolationLevel;
import com.rwto.mybatis.transaction.Transaction;
import com.rwto.mybatis.transaction.TransactionFactory;

import java.sql.SQLException;

/**
 * @author renmw
 * @create 2024/9/17 1:51
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            TransactionFactory transactionFactory = environment.getTransactionFactory();
            tx = transactionFactory.newTransaction(configuration.getEnvironment().getDataSource(), TransactionIsolationLevel.READ_COMMITTED, false);
            // 创建执行器
            final Executor executor = configuration.newExecutor(tx);
            // 创建DefaultSqlSession
            return new DefaultSqlSession(configuration, executor);
        } catch (Exception e) {
            try {
                assert tx != null;
                tx.close();
            } catch (SQLException ignore) {
            }
            throw new RuntimeException("Error opening session.  Cause: " + e);
        }
    }

}

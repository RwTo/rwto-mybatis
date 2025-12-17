package com.rwto.mybatis.datasource.executor;

import com.rwto.mybatis.datasource.executor.statement.StatementHandler;
import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.session.Configuration;
import com.rwto.mybatis.session.ResultHandler;
import com.rwto.mybatis.transaction.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author renmw
 * @since 2025/12/17 19:40
 **/
public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration, Transaction transaction) {
        super(configuration, transaction);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        try {
            Configuration configuration = ms.getConfiguration();
            StatementHandler handler = configuration.newStatementHandler(this, ms, parameter, resultHandler, boundSql);
            Connection connection = transaction.getConnection();
            Statement stmt = handler.prepare(connection);
            handler.parameterize(stmt);
            return handler.query(stmt, resultHandler);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}

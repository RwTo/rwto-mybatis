package com.rwto.mybatis.executor;

import com.rwto.mybatis.executor.statement.StatementHandler;
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
 * @create 2024/9/18 17:09
 * 简单执行器，
 * 1. 处理事务
 * 2. 执行sql 使用 StatementHandler 模板进行执行 1.准备语句，2.填充参数，3执行
 * 3. 解析结果  ResultHandler
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

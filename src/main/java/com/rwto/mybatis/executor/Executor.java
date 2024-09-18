package com.rwto.mybatis.executor;

import com.rwto.mybatis.mapping.BoundSql;
import com.rwto.mybatis.mapping.MappedStatement;
import com.rwto.mybatis.session.ResultHandler;
import com.rwto.mybatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author renmw
 * @create 2024/9/18 16:55
 **/
public interface Executor {
    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}

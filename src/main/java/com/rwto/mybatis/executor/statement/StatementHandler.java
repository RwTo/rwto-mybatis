package com.rwto.mybatis.executor.statement;

import com.rwto.mybatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author renmw
 * @create 2024/9/18 17:07
 **/
public interface StatementHandler {
    /**
     * 准备语句
     */
    Statement prepare(Connection connection) throws SQLException;

    /**
     * 参数化
     */
    void parameterize(Statement statement) throws SQLException;

    /**
     * 执行查询
     */
    <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException;
}

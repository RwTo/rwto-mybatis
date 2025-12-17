package com.rwto.mybatis.datasource.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author renmw
 * @since 2025/12/17 19:38
 **/
public interface ResultSetHandler {
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}

package com.rwto.mybatis.session;

import lombok.Getter;

import java.sql.Connection;

/**
 * 事务隔离级别
 * @author renmw
 * @since 2025/8/21 14:15
 **/
@Getter
public enum TransactionIsolationLevel {
    /**
     * 默认隔离级别
     */
    NONE(Connection.TRANSACTION_NONE),

    /**
     * 读未提交
     */
    READ_UNCOMMITTED(Connection.TRANSACTION_READ_UNCOMMITTED),

    /**
     * 读已提交
     */
    READ_COMMITTED(Connection.TRANSACTION_READ_COMMITTED),

    /**
     * 可重复读
     */
    REPEATABLE_READ(Connection.TRANSACTION_REPEATABLE_READ),

    /**
     * 串行化
     */
    SERIALIZABLE(Connection.TRANSACTION_SERIALIZABLE)
    ;

    private final int level;

    TransactionIsolationLevel(int level) {
        this.level = level;
    }

}

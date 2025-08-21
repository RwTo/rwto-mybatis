package com.rwto.mybatis.mapping;

import com.rwto.mybatis.transaction.TransactionFactory;
import lombok.*;

import javax.sql.DataSource;

/**
 * @author renmw
 * @since 2025/8/21 14:33
 **/
@Getter
@Builder
@AllArgsConstructor
public final class Environment {
    /**
     * 环境id
     */
    private final String id;
    /**
     * 事务工厂
     */
    private final TransactionFactory transactionFactory;
    /**
     * 数据源
     */
    private final DataSource dataSource;

    public static class Builder {

        private String id;
        private TransactionFactory transactionFactory;
        private DataSource dataSource;

        public Builder(String id) {
            this.id = id;
        }

        public Builder transactionFactory(TransactionFactory transactionFactory) {
            this.transactionFactory = transactionFactory;
            return this;
        }

        public Builder dataSource(DataSource dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public String id() {
            return this.id;
        }

        public Environment build() {
            return new Environment(this.id, this.transactionFactory, this.dataSource);
        }

    }
}

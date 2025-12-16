package com.rwto.mybatis.datasource.pooled;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author renmw
 * @since 2025/8/21 23:53
 **/
public class PooledConnection implements InvocationHandler {
    private static final String CLOSE = "close";
    private static final Class<?>[] IFACES = new Class<?>[]{Connection.class};

    private int hashCode = 0;
    private final PooledDataSource dataSource;

    // 真实的连接
    @Getter
    private final Connection realConnection;
    // 代理的连接
    @Getter
    private final Connection proxyConnection;

    @Getter
    @Setter
    private long checkoutTimestamp;
    @Getter
    @Setter
    private long createdTimestamp;
    @Getter
    @Setter
    private long lastUsedTimestamp;
    @Setter
    @Getter
    private int connectionTypeCode;
    private boolean valid;

    public PooledConnection(Connection connection, PooledDataSource dataSource) {
        this.hashCode = connection.hashCode();
        this.realConnection = connection;
        this.dataSource = dataSource;
        this.createdTimestamp = System.currentTimeMillis();
        this.lastUsedTimestamp = System.currentTimeMillis();
        this.valid = true;
        // 这里只能用jdk动态代理，因为connection是接口，需要拦截close方法（改为放入连接池）
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), IFACES, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        // 如果是调用 CLOSE 关闭链接方法，则将链接加入连接池中，并返回null
         /** 为什么先进行hashcode比较？  hashcode 比较的快，底层编码的习惯*/
         /** 默认的hashCode计算 与什么有关？ hashCode 官方文档显示：返回一个整数，在 Java 应用执行过程中保持一致。
          * 所以虽然不知道hashCode 是怎么计算的，可能与地址有关但可以肯定绝对不是地址，因为同一个对象地址是可能发生变化的（GC，新生代升到老年代）
          * */
        if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
            dataSource.pushConnection(this);
            return null;
        } else {
            if (!Object.class.equals(method.getDeclaringClass())) {
                // 除了Object自带的方法，其他方法调用之前要检查connection是否还是合法的,不合法要抛出SQLException
                // 这里防止连接回收后，再使用连接
                checkConnection();
            }
            // 其他方法交给connection去调用
            return method.invoke(realConnection, args);
        }
    }

    private void checkConnection() throws SQLException {
        if (!valid) {
            throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
        }
    }

    public void invalidate() {
        valid = false;
    }

    public boolean isValid() {
        return valid && realConnection != null && dataSource.pingConnection(this);
    }

    public int getRealHashCode() {
        return realConnection == null ? 0 : realConnection.hashCode();
    }

    /**
     * 获取自上次使用以来经过的时间
     * @return
     */
    public long getTimeElapsedSinceLastUse() {
        return System.currentTimeMillis() - lastUsedTimestamp;
    }

    public long getAge() {
        return System.currentTimeMillis() - createdTimestamp;
    }


    public long getCheckoutTime() {
        return System.currentTimeMillis() - checkoutTimestamp;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PooledConnection) {
            return realConnection.hashCode() == (((PooledConnection) obj).realConnection.hashCode());
        } else if (obj instanceof Connection) {
            return hashCode == obj.hashCode();
        } else {
            return false;
        }
    }
}

package com.rwto.mybatis.dao;

import com.rwto.mybatis.po.User;

/**
 * @author renmw
 * @create 2024/9/17 0:11
 **/
public interface UserDao {
    String getUserName(String userId);

    User getUserInfoById(String userId);
}

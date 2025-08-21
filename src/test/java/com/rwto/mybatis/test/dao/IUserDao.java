package com.rwto.mybatis.test.dao;

import com.rwto.mybatis.test.po.User;

/**
 * @author renmw
 * @since 2025/8/13 0:53
 **/
public interface IUserDao {

    User selectUserById(String id);
}

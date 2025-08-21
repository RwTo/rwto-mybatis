package com.rwto.mybatis.test.po;

import lombok.Data;

import java.util.Date;

/**
 * @author renmw
 * @since 2025/8/13 0:56
 **/
@Data
public class User {
    private Long id;
    // 用户ID
    private String userId;
    // 用户名称
    private String userName;
    // 头像
    private String userHead;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}

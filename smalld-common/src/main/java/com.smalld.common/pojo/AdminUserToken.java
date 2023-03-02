package com.smalld.common.pojo;

import lombok.Data;

@Data
public class AdminUserToken {
    private Long userId;

    private String userNo;

    private String userName;

    private String realName;

    private String source;

    private String email;

    private String tenant;
}

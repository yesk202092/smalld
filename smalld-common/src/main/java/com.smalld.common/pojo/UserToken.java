package com.smalld.common.pojo;

import lombok.Data;

@Data
public class UserToken {

    private Long userId;

    private String userNo;

    private String name;

    private String realName;

    private String source;
}

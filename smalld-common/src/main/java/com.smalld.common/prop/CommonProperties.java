
package com.smalld.common.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 通用基础配置
 *
 * @author yesk
 * @date 2022/1/2 17:03
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "smalld.config.common")
public class CommonProperties {

    /** 前端地址 */
    private String frontUrl;

    /** 后端地址 */
    private String backendUrl;
}

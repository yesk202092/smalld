
package com.smalld.config;

import com.smalld.core.filter.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author yesk
 * @date 2021-03-12 23:30
 */
@Configuration
public class FilterConfigure {

    private static final String MAX_AGE = "18000L";

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }

}

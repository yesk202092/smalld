package com.smalld.core.code.smalld;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.timeout}")
    private Integer timeout;
    @Value("${spring.redis.lockTimeOut:30000}")
    private Integer lockTimeOut;

    private static final String PREFIX = "redis://";

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        String address = PREFIX + host + ":" + port;
         config.useSingleServer()
                .setAddress(address).setPassword(password)
                .setTimeout(timeout).setPingConnectionInterval(3000).
                setDatabase(database);
        config.setLockWatchdogTimeout(lockTimeOut);
        return Redisson.create(config);
    }
}

package com.smalld;

import com.smalld.common.runner.AppStartupListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * SpringBoot方式 GateWayApplicationMain 启动类
 *
 * @author yesk
 * @date 2022/12/11 12:06
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplicationMain.class, args);
    }



    @Bean
    public AppStartupListener appStartupListener() {
        return new AppStartupListener();
    }


}

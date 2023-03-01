package com.smalld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {

    /**
     * 首页
     *
     * @author yesk
     * @date 2022/7/8 14:22
     **/
    @GetMapping("/")
    public String index() {
        return "THIS IS MY GATEWAY";
    }
}

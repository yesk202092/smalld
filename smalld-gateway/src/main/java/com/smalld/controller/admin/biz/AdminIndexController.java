package com.smalld.controller.admin.biz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminIndexController {
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

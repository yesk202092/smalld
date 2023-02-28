package com.smalld.${artifactId}.api;

import com.smalld.common.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 　　* @description: 这是一个接口
 　　* @author yesk
 　　* @date 2021/8/6 10:22
 　　*/
@FeignClient(value = "xxx-user",url = "user.smalld.com:port/")
public interface TestServer{
    /**
     *@Description 这是一个测试方法
     *@Author 叶少康
     *@Date 2021/8/6 10:30
     *@Params []
     *@Return com.smalld.marlin.common.base.DataResult<java.lang.Void>
     */
    @RequestMapping(value = "/server/test",method = RequestMethod.GET)
    CommonResult<Void> test();
}


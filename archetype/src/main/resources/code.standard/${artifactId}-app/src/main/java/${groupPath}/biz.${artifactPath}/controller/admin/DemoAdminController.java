package ${groupPath}.biz.${artifactPath}.controller.admin;

import com.smalld.common.pojo.CommonResult;
import ${groupPath}.biz.${artifactPath}.bo.base.IdBO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户登录接口区
 *
 * @author yesk
 * @date 2021/9/16  15:06
 */
@RestController
@RequestMapping("/admin/user")
@RefreshScope
public class DemoAdminController {

    /**
     * @param idBO
     * @return com.smalld.base.model.JsonResVo
     * @author 叶少康
     * @date 2021/9/15  18:14
     */
    @PostMapping(value = "/auth/demo")
    public CommonResult<Object> queryAccessToken(@RequestBody IdBO idBO) {
        return CommonResult.ok();
    }


}

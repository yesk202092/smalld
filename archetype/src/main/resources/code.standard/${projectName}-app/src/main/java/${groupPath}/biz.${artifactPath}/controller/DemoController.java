package ${groupPath}.biz.${artifactPath}.controller;

import com.smalld.common.pojo.DataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录接口区
 *
 * @author yesk
 * @date 2021/9/16  15:06
 */
@RestController
public class DemoController {

    /**
     * @param id
     * @return com.smalld.base.model.JsonResVo
     * @author 叶少康
     * @date 2021/9/15  18:14
     */
    @GetMapping(value = "/auth/query")
    public DataResult<Object> query(@RequestParam Long id) {
        return DataResult.success();
    }


}

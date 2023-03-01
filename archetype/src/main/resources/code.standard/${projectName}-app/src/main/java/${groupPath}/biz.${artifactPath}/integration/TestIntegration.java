package ${groupPath}.biz.${artifactPath}.integration;


import com.smalld.common.pojo.CommonResult;
import org.springframework.stereotype.Component;

/**
 * 分销商
 *
 * @author 叶少康
 * @date 2021/11/1 18:01
 */
@Component
public class TestIntegration {


    public CommonResult<Void> test() {
        return CommonResult.ok();
    }


}

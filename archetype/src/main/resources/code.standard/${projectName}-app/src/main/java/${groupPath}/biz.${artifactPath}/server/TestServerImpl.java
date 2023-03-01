package ${groupPath}.biz.${artifactPath}.server;

import com.smalld.common.pojo.CommonResult;
import ${groupPath}.${artifactPath}.api.TestServer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 分销商
 *
 * @author 叶少康
 * @date 2021/11/1 18:01
 */
@Component
public class TestServerImpl implements TestServer {


    @Override
    @RequestMapping(value = "/server/test",method = RequestMethod.GET)
    public CommonResult<Void> test() {
        return CommonResult.ok();
    }
}

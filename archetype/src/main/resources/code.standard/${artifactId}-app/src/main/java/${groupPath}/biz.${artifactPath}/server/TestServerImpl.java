package ${groupPath}.biz.${artifactPath}.server;

import com.smalld.common.pojo.CommonResult;
import ${groupPath}.${artifactPath}.api.TestServer;
import org.springframework.stereotype.Component;

/**
 * 分销商
 *
 * @author 叶少康
 * @date 2021/11/1 18:01
 */
@Component
public class TestServerImpl implements TestServer {


    @Override
    public CommonResult<Void> test() {
        return null;
    }
}

package ${groupPath}.biz.${artifactPath}.manager.assembly;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 模板模式接口初始化
 *
 * @author 叶少康
 * @date 2021/8/1117:49
 */
@Component
public class AuthAssemblyMap implements InitializingBean {
    /**
     * 支付宝
     */
    @Autowired
    TestAuthAssembly testAuthAssembly;


    private static Map<String, AuthAssembly> map = new HashMap<>();


    public static AuthAssembly getAuthAssemblybyMap(String key) {
        return map.get(key);
    }


    @Override
    public void afterPropertiesSet() {
        map.put("1", testAuthAssembly);


    }
}

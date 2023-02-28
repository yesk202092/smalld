package ${groupPath}.biz.${artifactPath}.manager.assembly;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 模板模式抽象类
 *
 * @author 叶少康
 * @date 2021/9/610:45
 */
public abstract class AbstractAuthFactory implements AuthAssembly<AuthFactoryResultContext, AuthFactoryContext> {


    @Override
    public AuthFactoryResultContext auth(AuthFactoryContext authFactoryContext) {
        AuthFactoryResultContext authFactoryResultContext = doAuth(authFactoryContext);

        return authFactoryResultContext;
    }

    /**
     * 执行授权
     *
     * @param authFactoryContext
     * @return com.smalld.business.user.manager.auth.AuthFactoryResultContext
     * @author 叶少康
     * @date 2021/9/17  14:27
     */
    protected abstract AuthFactoryResultContext doAuth(AuthFactoryContext authFactoryContext);

}

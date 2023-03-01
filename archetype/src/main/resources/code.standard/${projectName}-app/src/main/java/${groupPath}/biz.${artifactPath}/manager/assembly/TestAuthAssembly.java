package ${groupPath}.biz.${artifactPath}.manager.assembly;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestAuthAssembly extends AbstractAuthFactory{
    @Override
    protected AuthFactoryResultContext doAuth(AuthFactoryContext authFactoryContext) {
        return null;
    }
}

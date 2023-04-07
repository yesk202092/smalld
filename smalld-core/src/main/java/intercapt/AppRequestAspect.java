package intercapt;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
@Aspect
public class AppRequestAspect {
    //拦截除了后端以外的接口 暂无app端，有了之后再编辑
    @Pointcut("execution(* com.smalld..*Controller.*(..)) && !execution(* com.smalld..Admin*Controller.*(..)) && !execution(* com.smalld..admin..*Controller.*(..))")
    public void getMethod() {
    }
}

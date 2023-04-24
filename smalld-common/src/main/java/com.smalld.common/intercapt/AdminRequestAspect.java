package com.smalld.common.intercapt;

import com.smalld.common.annotation.PassToken;
import com.smalld.common.enums.CommonExceptionEnum;
import com.smalld.common.exception.CommonException;
import com.smalld.common.pojo.AdminUserToken;
import com.smalld.common.util.AdminSessionHelper;
import com.smalld.common.util.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
@Order(3)
@Aspect
public class AdminRequestAspect {
    private static final Logger log = LoggerFactory.getLogger(AdminRequestAspect.class);

    @Pointcut("execution(* com.smalld..Admin*Controller.*(..)) || execution(* com.smalld..admin..*Controller.*(..))")
    public void getMethod() {
    }

    @Around("getMethod()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = null;
        String methodName = null;
        boolean printRequest = true;

        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Annotation[][] annotations = method.getParameterAnnotations();
            for (Annotation[] annotation : annotations) {
                for (Annotation annotation1 : annotation) {
                    if (!((PassToken) annotation1).required()) {
                        obj = joinPoint.proceed();
                        return obj;
                    }
                }
            }
            String[] classNameArray = method.getDeclaringClass().getName().split("\\.");
            methodName = classNameArray[classNameArray.length - 1] + "." + method.getName();
            String params = this.buildParamsDefault(joinPoint);
            log.info("[APP_REQUEST] {}, params={}", methodName, printRequest ? params : "{}");
            AdminRequestAspect.TokenModel tokenModel = this.getToken();
            String token = tokenModel.getToken();
            if (StringUtils.isNotBlank(token)) {
                if (StringUtils.isBlank(token)) {
                    throw new CommonException(CommonExceptionEnum.ERROR401);
                }
                try {
                    //校验token
                    JwtUtils.verifyAdminToken(token, tokenModel.getTenant());
                    //解析token
                    AdminUserToken adminUserToken = JwtUtils.getAdminUserToken(token);
                    AdminSessionHelper.getInstance().setAdminUserVo(adminUserToken);
                } catch (Exception var20) {
                    log.error("Token解析异常:{}", var20.getMessage());
                    throw new CommonException(CommonExceptionEnum.ERROR401);
                }
            }
            obj = joinPoint.proceed();
        } finally {
            log.info("[APP_RESPONSE] {}, time={}ms, result={}", new Object[]{methodName, System.currentTimeMillis() - start});
        }
        AdminSessionHelper.getInstance().removeAdminUserVo();
        return obj;
    }


    private String buildParamsDefault(ProceedingJoinPoint call) {
        String params = "[";
        for (int i = 0; i < call.getArgs().length; ++i) {
            Object obj = call.getArgs()[i];
            if (i != call.getArgs().length - 1) {
                params = params + obj + ",";
            } else {
                params = params + obj + "]";
            }
        }

        return params;
    }

    private AdminRequestAspect.TokenModel getToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        AdminRequestAspect.TokenModel tokenModel = new AdminRequestAspect.TokenModel();
        if (requestAttributes == null) {
            return tokenModel;
        } else {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String javaToken = request.getHeader("token");
            String tenant = request.getHeader("tenant");
            tokenModel.setToken(javaToken);
            tokenModel.setTenant(tenant);
            return tokenModel;
        }
    }

    private static class TokenModel {
        private String token = "";
        private String uri;

        private String tenant;

        public String getTenant() {
            return tenant;
        }

        public void setTenant(String tenant) {
            this.tenant = tenant;
        }

        public TokenModel() {
        }

        public String getToken() {
            return this.token;
        }

        public String getUri() {
            return this.uri;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof AdminRequestAspect.TokenModel)) {
                return false;
            } else {
                AdminRequestAspect.TokenModel other = (AdminRequestAspect.TokenModel) o;
                if (!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$token = this.getToken();
                    Object other$token = other.getToken();
                    if (this$token == null) {
                        if (other$token != null) {
                            return false;
                        }
                    } else if (!this$token.equals(other$token)) {
                        return false;
                    }

                    Object this$uri = this.getUri();
                    Object other$uri = other.getUri();
                    if (this$uri == null) {
                        if (other$uri != null) {
                            return false;
                        }
                    } else if (!this$uri.equals(other$uri)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof AdminRequestAspect.TokenModel;
        }

        public int hashCode() {
            int PRIME = 1;
            int result = 1;
            Object $token = this.getToken();
            result = result * 59 + ($token == null ? 43 : $token.hashCode());
            Object $uri = this.getUri();
            result = result * 59 + ($uri == null ? 43 : $uri.hashCode());
            return result;
        }

        public String toString() {
            return "AdminRequestAspect.TokenModel( token=" + this.getToken() + ", uri=" + this.getUri() + ")";
        }
    }
}

package com.smalld.common.util;

import com.smalld.common.pojo.UserToken;
import org.slf4j.MDC;
/**
 * @author yesk
 */
public class SessionHelper {
    private static final ThreadLocal<UserToken> threadLocal = new ThreadLocal();

    public static SessionHelper getInstance() {
        return SessionHelper.ThreadLocalInstance.instance;
    }

    private SessionHelper() {
    }

    private static ThreadLocal<UserToken> getThreadLocal() {
        return threadLocal;
    }

    public void setUserVo(UserToken userToken) {
        getThreadLocal().set(userToken);
    }

    public static UserToken getAdminUserVo() {
        return (UserToken)getThreadLocal().get();
    }

    public void removeAdminUserVo() {
        getThreadLocal().remove();
        MDC.clear();
    }

    public static String getUserId() {
        UserToken userToken = getAdminUserVo();
        return userToken == null ? null : userToken.getUserId().toString();
    }

    private static class ThreadLocalInstance {
        private static final SessionHelper instance = new SessionHelper();

        private ThreadLocalInstance() {
        }
    }
}

package com.smalld.common.util;

import com.smalld.common.pojo.AdminUserToken;
import org.slf4j.MDC;

public class AdminSessionHelper {
    private static final ThreadLocal<AdminUserToken> threadLocal = new ThreadLocal();

    public static AdminSessionHelper getInstance() {
        return AdminSessionHelper.ThreadLocalInstance.instance;
    }

    private AdminSessionHelper() {
    }

    private static ThreadLocal<AdminUserToken> getThreadLocal() {
        return threadLocal;
    }

    public void setAdminUserVo(AdminUserToken adminUserVo) {
        getThreadLocal().set(adminUserVo);
    }

    public static AdminUserToken getAdminUserVo() {
        return (AdminUserToken)getThreadLocal().get();
    }

    public void removeAdminUserVo() {
        getThreadLocal().remove();
        MDC.clear();
    }

    public static String getUserId() {
        AdminUserToken adminUserVo = getAdminUserVo();
        return adminUserVo == null ? null : adminUserVo.getUserId().toString();
    }

    private static class ThreadLocalInstance {
        private static final AdminSessionHelper instance = new AdminSessionHelper();

        private ThreadLocalInstance() {
        }
    }
}

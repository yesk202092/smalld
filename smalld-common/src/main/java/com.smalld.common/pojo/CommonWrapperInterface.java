
package com.smalld.common.pojo;

import cn.hutool.json.JSONObject;

/**
 * 通用包装接口
 *
 * @author yesk
 * @date 2022/9/15 21:17
 */
public interface CommonWrapperInterface<T> {

    /**
     * 执行包装
     *
     * @author yesk
     * @date 2022/9/15 21:17
     */
    JSONObject doWrap(T wrapperObject);
}

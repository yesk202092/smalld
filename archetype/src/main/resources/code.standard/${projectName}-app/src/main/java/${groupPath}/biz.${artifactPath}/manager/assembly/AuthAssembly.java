package ${groupPath}.biz.${artifactPath}.manager.assembly;

/**
 * 模板
 *
 * @author 叶少康
 * @date 2021/9/610:45
 */
public interface AuthAssembly<R, T> {

    /**
     * 授权
     *
     * @param t
     * @return R
     * @author 叶少康
     * @date 2021/9/16  15:33
     */
    R auth(T t);

}

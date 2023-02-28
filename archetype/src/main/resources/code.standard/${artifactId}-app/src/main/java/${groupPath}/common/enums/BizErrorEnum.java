package ${groupPath}.common.enums;


/**
 * 异常码配置
 *
 * @author code-generator
 * @date 2021-09-09 15:46:08
 */
public enum BizErrorEnum {
    /**
     * 错误码分内外两种
     * 对内使用最细粒度错误吗，对外使用统一错误码
     * 对外统一使用本类型第一个错误码。
     */
    //1开头返回接收细节错误码,其它返回父错误码

    SYSTEM_ERROR(10000, 10000, "系统错误"),
    ERROR_BIZ_FAIL(20000, 20001, "业务失败"),
    ERROR_PARAM(12000, 12002, "参数校验错误"),
    ERROR_EXCPETION(12000, 12003, "解密异常"),

    ERROR_SOURCE_PARAM(12000, 12004, "参数校验错误,source不能为空"),
    ERROR_CODE_PARAM(12000, 12005, "参数校验错误,code不能为空"),
    ERROR_CHANNEL_PARAM(12000, 12006, "参数校验错误,Channel不能为空"),
    ERROR_REDIS_LOCK_TIME_OUT(15000, 15001, "系统开小差，请稍后再试"),

    //TODO 各业务模块自行替换下面的定义
    ERROR_FACEIN_VERIFY_CODE(30000, 30001, "请先获取验证码"),
    USER_NOT_EXISTS(30000, 30002, "用户不存在或已过期"),
    USER_STATUS_ERROR(30000, 30003, "账号未开通，请联系管理员 "),
    USER_EXISTS(30000, 30004, "用户已存在"),
    AUTH_SOURCE_IS_NULL(30000, 30005, "授权渠道不存在，请核对后重新发起"),
    AUTH_MOBILE_IS_NULL(30000, 30006, "请输入正确的手机号"),
    MOBILE_IS_NULL(30000, 30007, "手机号不能为空"),
    ACS_CODE_CHECK_ERROR(30000, 3008, "校验失败"),
    AUTH_CODE_ERROR(30000, 30009, "验证码不正确"),
    AUTH_CODE_NOT_NULL_ERROR(30000, 30010, "请勿重新发送验证码"),
    AUTH_CODE_NULL_ERROR(30000, 30011, "验证码不正确"),
    THIRD_SERVICE_ERROR(30000, 30012, "三方服务调用失败"),
    THIRD_SERVICE_RETURN_ERROR(30000, 30013, "三方服务返回失败"),
    THIRD_TABLE_IS_NULL(30000, 30014, "THIRD_TABLE不能为null"),
    SAVE_CODE_FAIL(30000, 30015, "发送验证码失败，请稍后再试"),
    APP_KEY_IS_NULL(30000, 30016, "appkey为空或不存在"),
    MOBILE_IS_ERROR(30000, 30017, "手机号已存在"),
    USER_MOBILE_IS_ERROR(30000, 30018, "此账号手机号已绑定"),
    CONFIG_IS_NULL(30000, 30019, "获取配置信息失败，请检查配置信息"),
    ERROR_USER_VERIFY(30000, 30020, "实名认证失败"),
    ERROR_NONE_USER_VERIFY(30000, 30021, "未进行实名认证"),
    ACCESS_TOKEN_GET_ERROR(30000, 30022, "获取ACCESS_TOKEN失败，请稍后再试"),

    SATISFACTION_POSITION_ERROR(40000, 40001, "反馈位置异常"),
    SATISFACTION_BUSINESS_TYPE_ERROR(40000, 40002, "反馈业务异常"),
    SATISFACTION_VALUE_ERROR(40000, 40003, "反馈内容异常"),
    SATISFACTION_DATA_NOTFOUND(40000, 40004, "内容不存在"),
    SATISFACTION_TYPE_ERROR(40000, 40005, "调研类型非法"),
    ERROR_NULL_EXCPETION(12000, 40006, "未获取到小程序应用手机号，请绑定手机号后再尝试"),
    ;


    private final int errorCode;
    private final int parentCode;
    private final String errorMessage;

    BizErrorEnum(int parentCode, int errorCode, String errorMessage) {
        this.parentCode = parentCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

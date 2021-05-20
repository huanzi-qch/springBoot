package cn.huanzi.qch.springbootexceptionhandler.pojo;

/**
 * 自定义异常枚举类
 */
public enum ErrorEnum {
    //自定义系列
    USER_NAME_IS_NOT_NULL("10001","【参数校验】用户名不能为空"),
    PWD_IS_NOT_NULL("10002","【参数校验】密码不能为空"),

    //400系列
    BAD_REQUEST("400","请求的数据格式不符!"),
    UNAUTHORIZED("401","登录凭证过期!"),
    FORBIDDEN("403","抱歉，你无权限访问!"),
    NOT_FOUND("404", "请求的资源找不到!"),

    //500系列
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    SERVICE_UNAVAILABLE("503","服务器正忙，请稍后再试!"),

    //未知异常
    UNKNOWN("10000","未知异常!");


    /** 错误码 */
    private String code;

    /** 错误描述 */
    private String msg;

    ErrorEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

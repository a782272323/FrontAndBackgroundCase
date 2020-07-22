package learn.lhb.oauth2.vue01.commons.enums;

/**
 * @Description  http 响应吗及状态枚举类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22
 * @time 14:19
 */
public enum  HttpResponseEnum {
    // 请求成功
    OK(200, "请求成功"),
    SAVE_OK(201, "保存成功"),
    PUT_OK(202, "编辑成功"),
    DELETE_OK(203, "删除成功"),
    // 未授权或者没有登录
    UNAUTHORIZED(401, "访问此资源需要完全的身份验证"),
    LOGIN_ERROR(402, "登录失败,请检查用户名或密码是否有错"),
    // 权限不足
    INSUFFICIENT_PERMISSIONS(403, "该用户权限不足以访问该资源接口"),
    FRONT_ERROR(404, "前端参数请求错误"),
    ACCESS_TOKEN_INVALID(411, "access_token无效"),
    REFRESH_TOKEN_INVALID(412,"refresh_token无效"),
    ACCOUNT_LOCKED(413, "账户被锁定，请联系管理员!"),
    ACCOUNT_DISABLE(414, "账户被禁用，请联系管理员"),
    // 系统异常，或者请求失败
    ERROR(500, "系统异常"),
    INCORRECT_PARAMS(501, "参数不正确,请重新输入"),
    ;

    /**
     * http 状态码
     */
    private int code;
    /**
     * 响应信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 构造器方法
     * @param code
     * @param message
     */
    HttpResponseEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 一般方法，
     * @param code
     * @return
     */
    public static HttpResponseEnum of (int code) {
        for (HttpResponseEnum temp : values()) {
            if (temp.getCode() == code) {
                return temp;
            }
        }
        return null;
    }

    /**
     * 通过 code 查找 message
     * @param code
     * @return
     */
    public static String getMessageByCode(int code) {
        for (HttpResponseEnum temp : values()) {
            if (temp.getCode() == code) {
                return temp.getMessage();
            }
        }
        return null;
    }


}

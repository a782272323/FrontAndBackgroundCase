package learn.lhb.oauth2.vue01.commons.constant;

/**
 * http 状态码 和 消息常量的封装
 *
 * @author 梁鸿斌
 * @date 2020/3/23.
 * @time 18:05
 */
public class HttpConstant {

    /* 状态码 */
    /**
     * 默认的成功的状态码
     */
    public static final int OK = 200;
    /**
     * 默认的失败的状态码
     */
    public static final int ERROR = 500;
    /**
     * 无权访问
     */
    public static final int NOT_ALLOW = 401;


    /* 常量 */
    /**
     * 默认成功返回的消息
     */
    public static final String MSG_DEFAULT_OK = "请求成功";
    /**
     * 默认失败返回的消息
     */
    public static final String MSG_DEFAULT_ERROR = "请求失败";
    /**
     * 默认系统异常返回的消息
     */
    public static final String MSG_DEFAULT_EXCEPTION = "系统异常，请联系管理员";
    /**
     * 用户名或者密码错误
     */
    public static final String MSG_USER_ERROR = "用户名或者密码错误";
    /**
     * 登录失败
     */
    public static final String MSG_LOGIN_ERROR = "登录失败,请检查用户名或密码是否有错";
    /**
     * 登录成功
     */
    public static final String MSG_LOGIN_OK = "登录成功";
    /**
     * 注销成功
     */
    public static final String MSG_LOGOUT_OK = "注销成功";
    /**
     * 用户名错误
     */
    public static final String MSG_USERNAME_ERROR = "用户名不存在";
    /**
     * 为登录
     */
    public static final String MSG_NOT_LOGIN = "您还没有登录，请登录";
    /**
     * 权限不足，无法访问
     */
    public static final String MSG_NOT_ALLOW = "权限不足，无法访问，请联系系统管理员";
    /**
     * 账户被锁定，请联系管理员!
     */
    public static final String MSG_ACCOUNT_LOCKED = "账户被锁定，请联系管理员!";
    /**
     * "密码过期，请联系管理员!"
     */
    public static final String MSG_CREDENTIAL_EXPIRED = "密码过期，请联系管理员!";
    /**
     * "账户过期，请联系管理员!"
     */
    public static final String MSG_ACCOUNT_EXPIRED = "账户过期，请重新登录!";
    /**
     * "账户被禁用，请联系管理员!"
     */
    public static final String MSG_ACCOUNT_DISABLE = "账户被禁用，请联系管理员!";
}

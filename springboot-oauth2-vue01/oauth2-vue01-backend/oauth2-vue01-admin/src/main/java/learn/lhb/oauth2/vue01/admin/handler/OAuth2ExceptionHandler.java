package learn.lhb.oauth2.vue01.admin.handler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @Description  登录失败异常处理类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 15:12
 */
@JsonSerialize(using = OAuth2ExceptionJacksonSerializerHandler.class)
public class OAuth2ExceptionHandler extends OAuth2Exception {

    private Integer oauth2ErrorCode;

    public OAuth2ExceptionHandler(String msg, Throwable t) {
        super(msg, t);
    }

    public OAuth2ExceptionHandler(String msg) {
        super(msg);
    }

    public Integer getOauth2ErrorCode() {
        return oauth2ErrorCode;
    }

    public void setOauth2ErrorCode(Integer oauth2ErrorCode) {
        this.oauth2ErrorCode = oauth2ErrorCode;
    }
}

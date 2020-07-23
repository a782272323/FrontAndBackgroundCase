package learn.lhb.oauth2.vue01.admin.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * @Description  异常翻译器
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 15:24
 */
@Component("oAuth2WebResponseExceptionTranslator")
public class OAuth2WebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        // 尝试从堆栈跟踪中提取Spring Security Exception
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);

        // 异常栈获取 OAuth2Exception 异常
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);

        // 判断异常栈中有是否存在 OAuth2Exception 登录失败的异常
        if (ase != null) {
            log.error("存在 OAuth2Exception 登录失败的异常");
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        // 判断异常栈中是否存在 AuthenticationException token失败或者失效的异常
        if (ase != null) {
            log.error("存在 AuthenticationException token失败或者失效的异常");
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        // 判断异常栈中是否存在 AccessDeniedException 权限不足的异常
        ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            log.error("存在 AccessDeniedException 权限不足的异常");
            return handleOAuth2Exception(new ForbiddenException(e.getMessage(), ase));
        }

        // 判断异常栈中是否存在 HttpRequestMethodNotSupportedException 请求方法错误的异常
        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            log.error("存在 HttpRequestMethodNotSupportedException 请求方法错误的异常");
            return handleOAuth2Exception(new MethodNotAllowed(e.getMessage(), ase));
        }

        // 不包含上述异常则服务器内部错误
        log.error("服务器内部错误");
        return handleOAuth2Exception(new ServerErrorException("系统内部异常，请联系管理员处理", e));
//        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    /**
     * 上述各种异常统一处理
     * @param e
     * @return
     * @throws IOException
     */
    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int code = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (code == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }

        OAuth2ExceptionHandler oAuth2ExceptionHandler = new OAuth2ExceptionHandler(e.getMessage(), e);
        ResponseEntity<OAuth2Exception> response = new ResponseEntity<OAuth2Exception>(oAuth2ExceptionHandler, headers, HttpStatus.valueOf(code));
        return response;
    }

    public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
        this.throwableAnalyzer = throwableAnalyzer;
    }

    /**
     * 处理 AccessDeniedException 权限不足的异常的内部类
     */
    @SuppressWarnings("serial")
    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
//            return "access_denied";
            return "该用户权限不足以访问该资源接口";
        }

        @Override
        public int getHttpErrorCode() {
            return 403;
        }
    }

    /**
     * 处理服务器内部错误的内部类
     */
    @SuppressWarnings("serial")
    private static class ServerErrorException extends OAuth2Exception {

        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
//            return "server_error";
            return "系统内部异常，请联系管理员处理";
        }

        @Override
        public int getHttpErrorCode() {
            return 500;
        }

    }

    /**
     * 处理 AuthenticationException token失败或者失效的异常的内部类
     */
    @SuppressWarnings("serial")
    private static class UnauthorizedException extends OAuth2Exception {

        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return "访问此资源需要完全的身份验证,请登录系统";
//            return "unauthorized";
        }

        @Override
        public int getHttpErrorCode() {
            return 401;
        }

    }

    /**
     * 处理 HttpRequestMethodNotSupportedException 请求方法错误的异常的内部类
     */
    @SuppressWarnings("serial")
    private static class MethodNotAllowed extends OAuth2Exception {

        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
//            return "method_not_allowed";
            return "接口请求方法不被允许访问";
        }

        @Override
        public int getHttpErrorCode() {
            return 405;
        }

    }
}

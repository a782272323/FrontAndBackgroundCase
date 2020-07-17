package learn.lhb.synrouter01.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的处理类，被JwtLoginFilter调用，JwtLoginFilter捕获
 * 到异常，就会调用它，并且把异常信息传给它
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:16
 */
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        this.errorType(response,exception);
    }

    /**
     * 捕获JwtLoginFilter抛出的异常并返回json给前端
     * @param response
     * @param exception
     * @throws IOException
     */
    public void errorType(HttpServletResponse response,
                          AuthenticationException exception) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(exception.getLocalizedMessage());
    }
}

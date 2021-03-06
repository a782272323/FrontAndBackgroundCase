package learn.lhb.oauth2.vue01.admin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 自定义异常处理类
 * 校验token是否过期
 * 用来解决匿名用户访问无权限资源时的异常,也就是跟token相关的资源异常
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 11:20
 */
public class AuthExceptionEntryHandler implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * token 验证
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        Throwable cause = authException.getCause();

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // CORS "pre-flight" request
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Cache-Control","no-cache");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");

        ObjectMapper mapper = new ObjectMapper();
        if (cause instanceof InvalidTokenException) {
            log.error("token无效,InvalidTokenException : {}", cause.getMessage());
            response.getWriter().write(mapper.writeValueAsString(BaseResult.error(411, "登录过期，请重新登录")));
        } else {
            log.error("没有登录");
            response.getWriter().write(mapper.writeValueAsString(BaseResult.error(401,"访问此资源需要完全的身份验证,请登录系统")));
        }

    }
}

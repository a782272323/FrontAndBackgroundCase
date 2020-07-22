package learn.lhb.oauth2.vue01.admin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import learn.lhb.oauth2.vue01.commons.enums.HttpResponseEnum;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description  CustomAuthExceptionHandler
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22
 * @time 14:00
 */
@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

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
            log.error("token无效,InvalidTokenException : {}",cause.getMessage());
            response.getWriter().write(mapper.writeValueAsString(BaseResult.error(411,"access_token无效")));
        }

        log.error("没有登录");
        response.getWriter().write(mapper.writeValueAsString(BaseResult.error(401,"访问此资源需要完全的身份验证")));

//        Map<String, Object> map = Maps.newHashMap();
//        // 权限不足
//        map.put("code", 401);
//        map.put("message", "权限不足");
//        map.put("path", request.getServletPath());
//        map.put("timestamp", DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now()));
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.getWriter().write(mapper.writeValueAsString(map));

    }

    /**
     * 权限认证
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Cache-Control","no-cache");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.addHeader("Access-Control-Max-Age", "1800");
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(BaseResult.error(403,"该用户权限不足以访问该资源接口")));

    }
}

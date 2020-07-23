package learn.lhb.oauth2.vue01.admin.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 11:20
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 访问失败/访问被拒绝 的处理
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
        log.error("权限不足");
        response.getWriter().write(mapper.writeValueAsString(BaseResult.error(403,"该用户权限不足以访问该资源接口")));

    }
}

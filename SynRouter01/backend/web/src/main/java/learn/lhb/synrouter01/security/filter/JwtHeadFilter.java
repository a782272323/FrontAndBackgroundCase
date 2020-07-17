package learn.lhb.synrouter01.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import learn.lhb.synrouter01.commons.constant.HttpConstant;
import learn.lhb.synrouter01.commons.utils.BaseResult;
import learn.lhb.synrouter01.commons.utils.MapperUtils;
import learn.lhb.synrouter01.domain.dto.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截请求进行token验证
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:14
 */
public class JwtHeadFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authentication");
        if (token==null || token.isEmpty()){
            filterChain.doFilter(request,response);
            return;
        }

        JwtUser user;
        try {

            String userJson = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            Gson gson = new Gson();
            user = gson.fromJson(userJson, JwtUser.class);

            //todo: 可以在这里添加检查用户是否过期,冻结...
        }catch (Exception e){
            //这里也可以filterChain.doFilter(request,response)然后return,那最后就会调用

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(MapperUtils.mapToJson(BaseResult.error(HttpConstant.MSG_ACCOUNT_EXPIRED)));
            return;
        }
        JwtLoginToken jwtLoginToken = new JwtLoginToken(user, "", user.getAuthorities());
        jwtLoginToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(jwtLoginToken);
        filterChain.doFilter(request,response);

    }

}


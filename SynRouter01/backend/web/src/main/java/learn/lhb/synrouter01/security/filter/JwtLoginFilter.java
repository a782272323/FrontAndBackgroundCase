package learn.lhb.synrouter01.security.filter;

import learn.lhb.synrouter01.commons.constant.HttpConstant;
import learn.lhb.synrouter01.commons.utils.BaseResult;
import learn.lhb.synrouter01.commons.utils.MapperUtils;
import learn.lhb.synrouter01.service.impl.JwtUserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JwtLoginFilter
 * 自定义的登录过滤器，把它加到SpringSecurity的过滤链中，拦截登录请求
 * 1.设置登录的url，请求的方式==定义这个过滤器要拦截哪个请求
 * 2.调用JwtAuthenticationProvider进行登录校验
 * 3.校验成功调用LoginSuccessHandler，校验失败调用LoginSuccessHandler
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:15
 */
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {



    private JwtUserDetailServiceImpl jwtUserDetailService;

    /**日志**/
    private static final Logger LOG = LoggerFactory.getLogger(JwtLoginFilter.class);


    public JwtLoginFilter() {
        /**
         * AntPathRequestMatcher
         * URL拦截
         */
        super(new AntPathRequestMatcher("/v1/user/login", "POST"));
    }


    /**
     * 拦截登录请求，具体url设置看上面，
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException 抛出异常，LoginFailureHandler捕获异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LOG.debug("--------------------------------------------");
            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            //创建未认证的凭证(etAuthenticated(false)),注意此时凭证中的主体principal为用户名
            JwtLoginToken jwtLoginToken = new JwtLoginToken(userName, password);
            //将认证详情(ip,sessionId)写到凭证
            jwtLoginToken.setDetails(new WebAuthenticationDetails(request));
            //AuthenticationManager获取受支持的AuthenticationProvider(这里也就是JwtAuthenticationProvider),
            //生成已认证的凭证,此时凭证中的主体为userDetails
            Authentication authenticatedToken = this.getAuthenticationManager().authenticate(jwtLoginToken);
            LOG.debug("拦截成功，并生成已经认证的凭证");
            System.out.println("拦截成功，并生成已经认证的凭证");
            return authenticatedToken;
        } catch (Exception e) {
            e.printStackTrace();

            throw new BadCredentialsException(MapperUtils.mapToJson(BaseResult.error(HttpConstant.MSG_USER_ERROR)));
        }

    }



}
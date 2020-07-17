package learn.lhb.synrouter01.security.provider;


import learn.lhb.synrouter01.security.filter.JwtLoginToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义认证器,账号密码的校验
 * 1.首先规定自己支持校验哪种凭证
 * 2.进行用户校验,调用JwtUserDetailServiceImpl 查询当前用户(JwtUser),判断用户账号密码是否正确,用户是否过期,被锁定等等
 * 3.若用户校验失败则抛异常给JwtLoginFilter,JwtLoginFilter捕获异常调用登录失败的处理类(LoginFailureHandler)
 * 4.若用户校验成功,则生成一个已认证的凭证,也就是Authentication,对应本例的JwtLoginToken 并返回给JwtLoginFilter,
 * JwtLoginFilter拿到凭证后调用登陆成功的处理类LoginSuccessHandler
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:17
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    /**
     * 供根据用户名查询用户,获取UserDetails的方法
     */
    private UserDetailsService userDetailsService;

    /**
     * 提供加密方式,密码验证时,需要加密后进行对比
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**日志**/
    public static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    /**
     * 认证提供者进行认证,注意这里传入的authentication对象,是JwtLoginFilter里调用
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     * * 此方法会返回一个已认证状态的authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

//
        //获取authentication中的用户密码
        logger.info("校验token*****");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("token里面的username = "+username);
        logger.info("token里面的password = "+password);


        //获取用户
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //转换authentication 认证时会先调用support方法,受支持才会调用,所以能强转
        JwtLoginToken jwtLoginToken = (JwtLoginToken) authentication;
        //检查
        defaultCheck(userDetails);
        additionalAuthenticationChecks(userDetails, jwtLoginToken);
        //构造已认证的authentication
        JwtLoginToken authenticatedToken = new JwtLoginToken(userDetails, jwtLoginToken.getCredentials(), userDetails.getAuthorities());
        authenticatedToken.setDetails(jwtLoginToken.getDetails());
        logger.trace("生产token成功");
        return authenticatedToken;
    }

    /**
     * 这个provider支持哪种凭证(token)的认证
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtLoginToken.class
                .isAssignableFrom(authentication));
    }

    /**
     * (附加检查项)检查密码是否正确
     */
    private void additionalAuthenticationChecks(UserDetails userDetails,
                                                JwtLoginToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("密码为空");
        }
        String presentedPassword = authentication.getCredentials().toString();
        System.out.println("userDetails.getPassword() = " + userDetails.getPassword().toString());
        System.out.println("presentedPassword = " + presentedPassword);
        System.out.println("加密 = " + passwordEncoder.encode(presentedPassword));
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            System.out.println("密码错误");
            throw new BadCredentialsException("密码错误");
        } else {
            System.out.println("密码正确");
        }
    }

    /**
     * 用户默认检查,用户是否锁定过期等
     */
    private void defaultCheck(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User is disabled");
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("User account has expired");
        }
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
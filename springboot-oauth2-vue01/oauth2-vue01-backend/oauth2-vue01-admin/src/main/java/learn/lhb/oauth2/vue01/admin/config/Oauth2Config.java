package learn.lhb.oauth2.vue01.admin.config;

import learn.lhb.oauth2.vue01.admin.handler.AuthExceptionEntryHandler;
import learn.lhb.oauth2.vue01.admin.handler.CustomAccessDeniedHandler;
import learn.lhb.oauth2.vue01.admin.handler.OAuth2WebResponseExceptionTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

/**
 * @Description  实现资源服务器与授权服务器，这里的资源服务器与授权服务器以内部类的形式实现。
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22
 * @time 10:55
 */
@Configuration
public class Oauth2Config {

    /**
     * 访问客户端密钥
     */
    public static final String CLIENT_SECRET = "123456";
    /**
     * 访问客户端ID
     */
    public static final String CLIENT_ID ="lhb";
    /**
     * 鉴权模式,密码模式，开启刷新token
     */
    public static final String[] GRANT_TYPE = {"password","refresh_token"};
    /**
     * 资源服务的ID，在单体写死，在微服务中根据服务来动态注入
     */
    public static final String RESOURCE_ID = "res1";

    /**
     * 资源服务器
     */
    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {


        @Autowired
        private OAuth2WebSecurityExpressionHandler expressionHandler;

        @Bean
        public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
            OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
            expressionHandler.setApplicationContext(applicationContext);
            return expressionHandler;
        }

        /**
         * 配置安全拦截机制
         * @param http
         * @throws Exception
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {

            http
                    // 配置会话（session）何时创建以及和Spring Security怎么进行交互
                    .csrf().disable()
                    // 配置会话（session）何时创建以及和Spring Security怎么进行交互,这里配置不用session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()

                    // 下边路径放行,不需要认证
                    // 门户网站前缀的接口，直接放行
                    .antMatchers("/portal/**").permitAll()
                    // 测试的接口，直接放行
                    .antMatchers("/test/**").permitAll()
                    // OPTIONS请求不需要鉴权
                    .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()

                    // 以下为配置所需保护的资源路径及权限，需要与认证服务器配置的授权部分对应
                    .antMatchers("/lhb").hasAnyAuthority("ppp")

            ;

            // 其余接口没有角色限制，但需要经过认证，只要携带token就可以放行
            http.authorizeRequests().anyRequest().authenticated();

        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources
                    .expressionHandler(expressionHandler)
                    // 资源id
                    .resourceId(RESOURCE_ID)
                    // 自定义异常处理,token过期或失效的处理
                    .authenticationEntryPoint(new AuthExceptionEntryHandler())
                    // 自定义异常处理,token权限不够的处理
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    // 在这些资源上只允许基于令牌的身份验证。(具体干哈不清楚)
//                    .stateless(true)
            ;

        }
    }


    /**
     * 认证授权服务器
     */
    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
        /**
         * 密码编码器
         */
        @Resource
        private BCryptPasswordEncoder passwordEncoder;

        /**
         * 认证管理器
         */
        @Resource
        private AuthenticationManager authenticationManager;

//        /**
//         * 令牌(暂时废弃)
//         */
//        @Resource
//        private TokenStore tokenStore;

        /**
         * 客户端详情
         */
        @Resource
        private ClientDetailsService clientDetailsService;

        @Autowired
        private RedisConnectionFactory connectionFactory;

        @Autowired
        private OAuth2WebResponseExceptionTranslator oAuth2WebResponseExceptionTranslator;

        /**
         * 使用redis来存储令牌
         * @return
         */
        @Bean
        public RedisTokenStore redisTokenStore() {
            return new RedisTokenStore(connectionFactory);
        }

        /**
         * 客户端详情配置
         * 暂时使用内存存储
         * @param clients
         * @throws Exception
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            // 基于内存存储
            clients.inMemory()
                    // client_id
                    .withClient(CLIENT_ID)
                    // 加密
                    .secret(passwordEncoder.encode(CLIENT_SECRET))
                    // 资源id
                    .resourceIds(RESOURCE_ID)
                    // 授权模式
                    .authorizedGrantTypes(GRANT_TYPE[0], GRANT_TYPE[1])
                    // 允许授权的范围
                    .scopes("all")
            ;

        }

        /**
         * 配置令牌访问端点和令牌访问服务，令牌 = token
         * @param security
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security
                    // 开启/oauth/token_key验证端口无权限访问
                    .tokenKeyAccess("permitAll()")
                    // /oauth/check_token 公开
                    .checkTokenAccess("permitAll()")
                    // 允许表单验证（前端），申请令牌
                    .allowFormAuthenticationForClients()
            ;


        }

        /**
         * 配置令牌端点的安全约束
         * 启动授权终端
         * @param endpoints
         * @throws Exception
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            //token信息存到服务内存
//            endpoints.tokenStore(new InMemoryTokenStore())
//                    .authenticationManager(authenticationManager);

            endpoints
                    // 令牌存redis
                    .tokenStore(redisTokenStore())
                    // 认证管理器,支持密码模式
                    .authenticationManager(authenticationManager)
                    // 允许post请求访问令牌
                    .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                    // 校验token
                    .tokenServices(tokenServices())
                    ;

            // 异常翻译器
            endpoints.exceptionTranslator(oAuth2WebResponseExceptionTranslator);
        }

        /**
         * 令牌设置
         * @return
         */
        @Bean
        public AuthorizationServerTokenServices tokenServices() {
            DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            // 客户端详情信息
            defaultTokenServices.setClientDetailsService(clientDetailsService);
            // 开启刷新令牌
            defaultTokenServices.setReuseRefreshToken(true);
            // 令牌存储方案
            defaultTokenServices.setTokenStore(redisTokenStore());
            // 令牌 assess_token 有效期12小时，可自定义
            defaultTokenServices.setAccessTokenValiditySeconds(60 * 60 * 12);
            // 刷新令牌 refresh_token 有效期一周，可自定义
            defaultTokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
            return defaultTokenServices;
        }
    }



}

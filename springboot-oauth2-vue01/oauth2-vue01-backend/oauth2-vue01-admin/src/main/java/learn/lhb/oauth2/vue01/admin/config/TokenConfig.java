package learn.lhb.oauth2.vue01.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Description  令牌
 * 不基于内存存储时就废弃了。
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22 
 * @time 11:35
 */
@Configuration
public class TokenConfig {

    /**
     * 暂时使用内存来存储令牌
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }
}

package learn.lhb.oauth2.vue01.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Description  security 登录校验逻辑实现类
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/23
 * @time 09:40
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * 登录校验与授权
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String password = passwordEncoder.encode("123456");
        log.trace("password = {}", password);
        UserDetails userDetails =
                User.withUsername("lhb")
                    .password(password).authorities("ppp").build();
        return userDetails;
    }
}

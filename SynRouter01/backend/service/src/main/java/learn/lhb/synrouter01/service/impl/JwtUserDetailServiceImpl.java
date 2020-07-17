package learn.lhb.synrouter01.service.impl;


import learn.lhb.synrouter01.domain.dto.JwtUser;
import learn.lhb.synrouter01.domain.entity.SysAccountEntity;
import learn.lhb.synrouter01.mapper.SysAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * UserDetailsService的实现
 * JwtAuthenticationProvider在进行登录信息校验是就会通过它查询用户信息
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:18
 */
@Component
public class JwtUserDetailServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Resource
    private SysAccountMapper sysAccountMapper;

    /**日志**/
    private static final Logger LOG = LoggerFactory.getLogger(JwtUserDetailServiceImpl.class);

    @Lazy(true)
    @Autowired
    public JwtUserDetailServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 数据库查询
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 校验数据库中用户名是否存在
        SysAccountEntity entity = sysAccountMapper.getSysAccountByUsername(username);
        LOG.debug("校验数据库中用户名是否存在 = " + entity.toString());
        if (entity != null) {
            return new JwtUser(entity.getUsername(), entity.getPassword());
        }
        return null;


    }
}

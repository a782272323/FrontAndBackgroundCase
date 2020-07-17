package learn.lhb.synrouter01.service.impl;

import learn.lhb.synrouter01.domain.entity.SysIgnoreUrlEntity;
import learn.lhb.synrouter01.domain.entity.SysResourcesEntity;
import learn.lhb.synrouter01.domain.entity.SysRoleEntity;
import learn.lhb.synrouter01.mapper.SysIgnoreUrlMapper;
import learn.lhb.synrouter01.mapper.SysResourcesMapper;
import learn.lhb.synrouter01.mapper.SysRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 配置路径访问限制,若你的用户角色比较简单,不需要存数据库,
 * 可以在ApplicationConfigurerAdapter里配置如
 * httpSecurity
 * .authorizeRequests()
 * .antMatchers("/order").....
 *
 * @author 梁鸿斌
 * @date 2020/3/25.
 * @time 10:24
 */
@Component("accessDecisionService")
public class AccessDecisionService {

    public static final Logger LOG = LoggerFactory.getLogger(AccessDecisionService.class);

    @Resource
    private SysIgnoreUrlMapper sysIgnoreUrlMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysResourcesMapper sysResourcesMapper;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        // 不被拦截的路径
        List<SysIgnoreUrlEntity> sysIgnoreUrlEntities = sysIgnoreUrlMapper.getIgnoreUrl();
        String[] noUrl = new String[sysIgnoreUrlEntities.size()];
        for (int i = 0; i < sysIgnoreUrlEntities.size(); i++) {
            noUrl[i] = sysIgnoreUrlEntities.get(i).getUrl();
        }
        for (String url : Arrays.asList(noUrl)) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 根据用户名查出拥有哪些权限
        List<String> urls = queryUrlByUserName(userDetails.getUsername());
        for (String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从数据库查询用户权限
     */
    private List<String > queryUrlByUserName(String username) {
        SysRoleEntity roleEntity = sysRoleMapper.getRoleNameByUsername(username);
        LOG.trace(" 权限名 = " + roleEntity.getName());
        List<SysResourcesEntity> resources = sysResourcesMapper.getUrlByRoleName(roleEntity.getName());
        String[] strings = new String[resources.size()];
        for (int i = 0; i < resources.size(); i++) {
            strings[i] = resources.get(i).getUrl();
        }
        // todo Arrays用法百度
        return Arrays.asList(strings);
    }
}
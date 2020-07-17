package learn.lhb.synrouter01.controller;

import com.google.common.collect.Maps;
import learn.lhb.synrouter01.commons.constant.HttpConstant;
import learn.lhb.synrouter01.commons.utils.BaseResult;
import learn.lhb.synrouter01.domain.dto.JwtUser;
import learn.lhb.synrouter01.domain.vo.LoginInfoVo;
import learn.lhb.synrouter01.mapper.SysRoleMapper;
import learn.lhb.synrouter01.mapper.SysRouterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description  登录模块 controller
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:44
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("v1/user")
public class LoginController {

    // 日志
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRouterMapper sysRouterMapper;

    /**
     * 登录成功后跳转首页
     *
     * @param authentication
     * @return
     */
    @GetMapping("info")
    public BaseResult info(Authentication authentication) {
        LoginInfoVo loginInfoVo = new LoginInfoVo();
        loginInfoVo.setName(authentication.getName());
        loginInfoVo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        loginInfoVo.setRoles(sysRoleMapper.getRoleNameByUsername(authentication.getName()).getName());
        return BaseResult.ok().put(HttpConstant.OK, HttpConstant.MSG_DEFAULT_OK, "data", loginInfoVo);
    }

    /**
     * 注销
     * @param authentication
     * @return
     */
    @PostMapping("logout")
    public BaseResult logout(Authentication authentication) {
        JwtUser jwtUser =(JwtUser) authentication.getPrincipal();
        LOG.info(" Authentication = " + authentication.toString());
        LOG.info(" 登录的用户名 = " + authentication.getName());
        LOG.info(" 密码 = " + jwtUser.getPassword());
        LOG.info(" 权限 = " + jwtUser.getAuthorities());

        return BaseResult.ok();
    }

    /**
     * 获取路由表
     * @param authentication
     * @return
     */
    @GetMapping("router")
    public BaseResult router(Authentication authentication) {
        String username = authentication.getName();
        LOG.info("username = " + username);
        Map<String, Object> map = Maps.newHashMap();
        map.put("getRouterTree", sysRouterMapper.getRouterTree(sysRoleMapper.getRoleNameByUsername(username).getName()));
        return BaseResult.ok().put(HttpConstant.OK,HttpConstant.MSG_DEFAULT_OK,"data",map);
    }
}

package learn.lhb.synrouter01.mapper;

import learn.lhb.synrouter01.domain.entity.SysRoleEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description  表 sys_role mapper
 * @author Herbie Leung
 * @date 2020/7/16
 * @time 13:50
 */
@Repository
public interface SysRoleMapper {

    /**
     * 用户名获取对应的角色name
     * @param username
     * @return
     */
    SysRoleEntity getRoleNameByUsername(String username);
}

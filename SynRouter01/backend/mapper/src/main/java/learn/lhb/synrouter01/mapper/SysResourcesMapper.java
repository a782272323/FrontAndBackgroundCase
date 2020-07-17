package learn.lhb.synrouter01.mapper;

import learn.lhb.synrouter01.domain.entity.SysResourcesEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  表 sys_resources mapper
 * @author Herbie Leung
 * @date 2020/7/16
 * @time 13:53
 */
@Repository
public interface SysResourcesMapper {

    /**
     * 根据角色名查询能访问的权限
     * @param roleName
     * @return
     */
    List<SysResourcesEntity> getUrlByRoleName(String roleName);
}

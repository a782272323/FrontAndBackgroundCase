package learn.lhb.synrouter01.mapper;

import learn.lhb.synrouter01.domain.vo.SysRouterVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  表 sys_router mapper
 * @author Herbie Leung
 * @date 2020/7/16
 * @time 13:54
 */
@Repository
public interface SysRouterMapper {

    /**
     * 根据角色名，获取用户对应的路由树（路由表）
     * 递归查询
     * @param roleName
     * @return
     */
    List<SysRouterVo> getRouterTree(@Param("roleName") String roleName);
}

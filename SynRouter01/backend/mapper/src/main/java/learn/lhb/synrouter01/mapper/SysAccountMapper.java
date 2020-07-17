package learn.lhb.synrouter01.mapper;

import learn.lhb.synrouter01.domain.entity.SysAccountEntity;
import org.springframework.stereotype.Repository;

/**
 * @Description  用户表 mapper
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:38
 */
@Repository
public interface SysAccountMapper {

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    SysAccountEntity getSysAccountByUsername(String username);
}

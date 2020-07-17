package learn.lhb.synrouter01.mapper;

import learn.lhb.synrouter01.domain.entity.SysIgnoreUrlEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description  表 sys_ignore_url mapper
 * @author Herbie Leung
 * @date 2020/7/16
 * @time 13:48
 */
@Repository
public interface SysIgnoreUrlMapper {

    /**
     * 获取 ignore url
     * @return
     */
    List<SysIgnoreUrlEntity> getIgnoreUrl();
}

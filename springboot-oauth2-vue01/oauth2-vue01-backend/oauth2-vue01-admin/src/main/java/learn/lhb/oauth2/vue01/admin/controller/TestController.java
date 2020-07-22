package learn.lhb.oauth2.vue01.admin.controller;

import com.google.common.collect.Maps;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import learn.lhb.oauth2.vue01.commons.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description  测试
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22
 * @time 17:40
 */
@RestController
@RequestMapping("test")
public class TestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtils redisUtils;

    /**
     * 测试缓存的使用
     * @return
     */
    @GetMapping("redis01")
    public BaseResult redisTest01() {
        String key = "201735020325";
        String username = "梁鸿斌";
        String password = "123456";
        Map<String, Object> map = Maps.newHashMap();
        map.put("username" , username);
        map.put("password" , password);

        // 先查询缓存是否有数据
        if (redisUtils.hmget(key) != null && redisUtils.hmget(key).size() > 0) {
            log.info("缓存没有数据");
            return BaseResult.ok().put(200,"缓存没有数据", "data", redisUtils.hmget(key));
        }

        log.info("缓存没有数据，存入缓存");
        if (redisUtils.hmset(key,map,60 * 60 * 12) == true) {
            return BaseResult.ok("存入缓存");
        }

        return BaseResult.error("缓存服务器异常");
    }
}

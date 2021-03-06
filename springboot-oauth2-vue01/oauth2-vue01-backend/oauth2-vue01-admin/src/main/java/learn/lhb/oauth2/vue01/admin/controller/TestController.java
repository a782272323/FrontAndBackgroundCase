package learn.lhb.oauth2.vue01.admin.controller;

import com.google.common.collect.Maps;
import learn.lhb.oauth2.vue01.commons.enums.HttpResponseEnum;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import learn.lhb.oauth2.vue01.commons.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
public class TestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("test")
    public BaseResult test1() {
        return BaseResult.ok("测试集成部署1");
    }

    @GetMapping("lhb")
    public BaseResult test2(Authentication authentication) {
        log.debug("测试");
        log.trace("Authentication = " + authentication);
        log.trace(authentication.getName());
        log.debug(authentication.getAuthorities().toString());
        return BaseResult.ok("测试,token成功");
    }

    /**
     * 测试缓存的使用
     * @return
     */
    @GetMapping("/test/redis01")
    public BaseResult redisTest01() {
        String key = "201735020325";
        String username = "梁鸿斌";
        String password = "123456";
        Map<String, Object> map = Maps.newHashMap();
        map.put("username" , username);
        map.put("password" , password);

        // 先查询缓存是否有数据
        if (redisUtils.hmget(key) != null && redisUtils.hmget(key).size() > 0) {
            log.info("缓存有数据");
            return BaseResult.ok().put(200,"缓存有数据", "data", redisUtils.hmget(key));
        }

        log.info("缓存没有数据，存入缓存");
        if (redisUtils.hmset(key,map,60 * 60 * 12) == true) {
            return BaseResult.ok("缓存没有数据,存入缓存");
        }

        return BaseResult.error("缓存服务器异常");
    }
}

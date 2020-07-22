package learn.lhb.oauth2.vue01.admin.controller;

import learn.lhb.oauth2.vue01.commons.enums.HttpResponseEnum;
import learn.lhb.oauth2.vue01.commons.utils.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description  登录 controller
 * @author Herbie Leung(梁鸿斌)
 * @date 2020/7/22
 * @time 15:25
 */
@RestController
public class LoginController {


    @GetMapping("test")
    public BaseResult test1() {
        return BaseResult.ok(HttpResponseEnum.OK.toString());
    }

    @GetMapping("portal/test")
    public BaseResult test2() {
        return BaseResult.ok("测试绕过token成功");
    }
}

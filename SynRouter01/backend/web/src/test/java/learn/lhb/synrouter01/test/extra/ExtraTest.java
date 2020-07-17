package learn.lhb.synrouter01.test.extra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description  额外的测试类。
 * 比如测试 md5 密码加密等等.
 * @author Herbie Leung
 * @date 2020/6/19
 * @time 01:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExtraTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * PasswordEncoder 加密测试
     */
    @Test
    public void passwordEncoderTest() {
        String password = "123546";

        System.out.println(passwordEncoder.encode(password));
//        if (!passwordEncoder.matches(password, passwordEncoder.encode(password))) {
        if (!passwordEncoder.matches(password, "$2a$10$CPG3lK5cdsLiN6SguMcu/.nWMEWIqKVk93uBWhmpRwNEjql3L53dm")) {
            System.out.println("密码错误");
        } else {
            System.out.println("密码正确");
        }
    }
}

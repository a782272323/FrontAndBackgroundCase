package learn.lhb.synrouter01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description  启动类
 * @author Herbie Leung
 * @date 2020/7/15
 * @time 16:45
 */
@SpringBootApplication(scanBasePackages = "learn.lhb.synrouter01")
@MapperScan(basePackages = "learn.lhb.synrouter01.mapper")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}

package learn.lhb.oauth2.vue01.admin;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "learn.lhb.oauth2.vue01.mapper")
public class Oauth2Vue01AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(Oauth2Vue01AdminApplication.class, args);
    }
}

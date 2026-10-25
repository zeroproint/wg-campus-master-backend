package com.wg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WgAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WgAdminApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Wg-campus启动成功   ლ(´ڡ`ლ)ﾞ");

    }

}

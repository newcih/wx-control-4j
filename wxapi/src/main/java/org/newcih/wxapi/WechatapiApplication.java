package org.newcih.wxapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author NEWCIH
 */
@EnableScheduling
@SpringBootApplication
@EnableCaching
public class WechatapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatapiApplication.class, args);
    }

}

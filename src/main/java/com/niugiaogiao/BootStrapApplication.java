package com.niugiaogiao;

import com.niugiaogiao.core.hotspot.HotSpotRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy
public class BootStrapApplication implements CommandLineRunner {

    @Autowired
    HotSpotRunner hotSpotRunner;

    public static void main(String[] args) {
        SpringApplication.run(BootStrapApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        hotSpotRunner.run();
    }
}

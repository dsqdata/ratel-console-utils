package org.ratel.console.utils;

import lombok.extern.slf4j.Slf4j;
import org.ratel.console.utils.base.RatelBaseProcess;
import org.ratel.console.utils.process.MainRatelProcess;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

    }

    public void run(String... arg0) throws Exception {
        RatelBaseProcess process = new MainRatelProcess();
        process.process();
    }
}
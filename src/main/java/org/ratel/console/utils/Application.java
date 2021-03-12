package org.ratel.console.utils;

import lombok.extern.slf4j.Slf4j;
import org.ratel.console.utils.process.RatelToolProcess;
import org.ratel.console.utils.process.impl.HdfsRatelToolProcess;
import org.ratel.console.utils.process.impl.IpRatelToolProcess;
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
        log.info("\n----------------------------------------------------------\n\t" +
                        "应用 '{}' 运行成功! \n\t" +
                        "1、{}\n\t" +
                        "2、{}\n" +
                        "----------------------------------------------------------", "Ratel-工具",
                "hdfs工具", "本机 IP"
        );
        String toolNumber = RatelToolProcess.getScannerInput("请输入工具序号:\n");
        RatelToolProcess ratelToolProcess = null;
        switch (toolNumber) {
            case "1":
                ratelToolProcess = new HdfsRatelToolProcess();
                break;
            case "2":
                ratelToolProcess = new IpRatelToolProcess();
                break;
            default: //可选
                System.out.println("请正确输入工具序号！");
                break;
        }
        if (ratelToolProcess != null) {
            ratelToolProcess.process();
        }
        System.out.println("程序执行完成！");
    }


    public void processHdfsTools() throws Exception {

    }
}
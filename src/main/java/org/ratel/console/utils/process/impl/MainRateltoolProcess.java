package org.ratel.console.utils.process.impl;

import lombok.extern.slf4j.Slf4j;
import org.ratel.console.utils.process.RatelToolProcess;

@Slf4j
public class MainRateltoolProcess extends RatelToolProcess {


    @Override
    public void beforeProcess() throws Exception {
        String[] args = new String[]{"Ratel-工具", "退出工具", "hdfs工具", "本机 IP"};
        log.info("\n----------------------------------------------------------\n\t" +
                "应用 '{}' 运行成功! \n\t" +
                "0、{}\n\t" +
                "1、{}\n\t" +
                "2、{}\n" +
                "----------------------------------------------------------", args
        );
    }

    @Override
    public void processOperation() throws Exception {
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
        if (!"0".equals(toolNumber)) {
            this.process();
        }
        System.out.println("程序执行完成！");
    }
}

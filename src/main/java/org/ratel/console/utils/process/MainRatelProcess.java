package org.ratel.console.utils.process;

import lombok.extern.slf4j.Slf4j;
import org.ratel.console.utils.base.RatelBaseMainProcess;
import org.ratel.console.utils.base.RatelBaseProcess;
import org.ratel.console.utils.process.hdfs.HdfsRatelProcess;
import org.ratel.console.utils.process.system.IpRatelProcess;
import org.ratel.console.utils.process.system.SystemRatelProcess;

@Slf4j
public class MainRatelProcess extends RatelBaseMainProcess {


    @Override
    public void beforeProcess() throws Exception {
        String[] args = new String[]{"Ratel-工具", "退出工具", "hdfs工具", "本机 IP", "操作系统信息"};
        log.info("\n----------------------------------------------------------\n\t" +
                "应用 '{}' 运行成功! \n\t" +
                "0、{}\n\t" +
                "1、{}\n\t" +
                "2、{}\n\t" +
                "3、{}\n" +
                "----------------------------------------------------------", args
        );
    }

    @Override
    public void processOperation() throws Exception {
        String toolNumber = RatelBaseProcess.getScannerInput("请输入工具序号:\n");
        RatelBaseProcess ratelToolProcess = null;
        switch (toolNumber) {
            case "1":
                ratelToolProcess = new HdfsRatelProcess();
                break;
            case "2":
                ratelToolProcess = new IpRatelProcess();
                break;
            case "3":
                ratelToolProcess = new SystemRatelProcess();
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

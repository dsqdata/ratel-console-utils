package org.ratel.console.utils.process.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.ratel.console.utils.process.RatelToolProcess;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;

@Slf4j
public class HdfsRatelToolProcess extends RatelToolProcess {
    FileSystem fileSystem = null;

    @Override
    public void beforeProcess() throws Exception {
    }

    @Override
    public void processOperation() throws Exception {
        String hdfsUri = getScannerInput("请输入Hdfs 链接 (例：172.16.36.222:9000):\n");
        hdfsUri = !StringUtils.isEmpty(hdfsUri) ? hdfsUri : "172.16.36.222:9000";
        String hdfsUser = getScannerInput("请输入Hdfs 用户 (例：root):\n");
        hdfsUser = !StringUtils.isEmpty(hdfsUser) ? hdfsUser : "root";
        fileSystem = getFileSystem(hdfsUri, hdfsUser);
        processOperationSub();
    }

    public void processOperationSub() throws Exception {
        beforeProcessSub();
        processSub();
    }

    public void beforeProcessSub() throws Exception {
        String[] args = new String[]{"返回上一层", "上传文件", "下载文件", "删除文件", "创建文件夹", "文件列表"};
        log.info("\n----------------------------------------------------------\n\t" +
                "FDFS 链接成功! \n\t" +
                "0、{}\n\t" +
                "1、{}\n\t" +
                "2、{}\n\t" +
                "3、{}\n\t" +
                "4、{}\n\t" +
                "5、{}\n" +
                "----------------------------------------------------------", args
        );
    }

    public void processSub() throws Exception {
        String toolNumber = getScannerInput("请选择功能:\n");
        switch (toolNumber) {
            case "1":
                String hdfsPath = getScannerInput("请输入Hdfs 路径 (例：/ratel):\n");
                hdfsPath = !StringUtils.isEmpty(hdfsPath) ? hdfsPath : "/ratel";
                String localFilePath = getScannerInput("请输入上传文件完整路径 (例：/Downloads/ratel-master.zip):\n");
                if (StringUtils.isEmpty(localFilePath)) {
                    System.out.println("上传文件完整路径为空！");
                    break;
                }
                fileSystem.copyFromLocalFile(new Path(localFilePath), new Path(hdfsPath + "/"));
                break;
            case "2":
                String downloadFilePath = getScannerInput("请输入下载文件完整路径 (例：hdfs://172.16.36.222:9000/test/11.pdf):\n");
                if (StringUtils.isEmpty(downloadFilePath)) {
                    System.out.println("下载文件完整路径为空！");
                    break;
                }
                String loFilePath = getScannerInput("请输入本地路径 (例：/Downloads):\n");
                if (StringUtils.isEmpty(loFilePath)) {
                    System.out.println("本地路径为空！");
                    break;
                }
                fileSystem.copyToLocalFile(false, new Path(downloadFilePath), new Path(loFilePath), true); //下载到D盘
                break;
            case "3":
                String deleteFilePath = getScannerInput("请输入删除文件完整路径 (例：/test/11.pdf):\n");
                if (StringUtils.isEmpty(deleteFilePath)) {
                    System.out.println("删除文件完整路径为空！");
                    break;
                }
                fileSystem.delete(new Path(deleteFilePath), true);
                break;
            case "4":
                String creatFilePath = getScannerInput("请输入创建文件夹完整路径 (例：/test/11.pdf):\n");
                if (StringUtils.isEmpty(creatFilePath)) {
                    System.out.println("创建文件夹完整路径为空！");
                    break;
                }
                fileSystem.mkdirs(new Path(creatFilePath));
                break;
            case "5":
                String findFilePath = getScannerInput("请输入查询文件完整路径 (例：/test/11.pdf):\n");
                if (StringUtils.isEmpty(findFilePath)) {
                    System.out.println("查询文件完整路径为空！");
                    break;
                }
                System.out.println("正在查询文件... ...");
                FileStatus[] statuses = fileSystem.listStatus(new Path(findFilePath));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (FileStatus file : statuses) {
                    System.out.println(format.format(file.getModificationTime()) + "  " + file.getPermission() + "  " + file.getPath() + "    " + file.getLen() + "k  " + (file.isDirectory() ? "文件夹" : "文件"));
                }
                break;
            default: //可选
                System.out.println("请正确输入工具序号！");
                break;
        }
        if (!"0".equals(toolNumber)) {
            processOperationSub();
        }
    }


    public FileSystem getFileSystem(String hdfsUri, String hdfsUser) throws URISyntaxException, IOException, InterruptedException {
        String HDFS = "hdfs://" + hdfsUri;
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", HDFS);//在配置文件conf中指定所用的文件系统---HDFS
        FileSystem fileSystem = FileSystem.get(new URI(HDFS), conf, hdfsUser);
        fileSystem.setWorkingDirectory(new Path(HDFS + "/"));//设置工作目录，默认是在user目录下,这里设置所有根目录下
        return fileSystem;
    }
}

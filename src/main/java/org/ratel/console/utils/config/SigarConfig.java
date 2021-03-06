package org.ratel.console.utils.config;

import lombok.extern.slf4j.Slf4j;
import org.hyperic.jni.ArchNotSupportedException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.*;

@Slf4j
@Configuration
public class SigarConfig {

    //静态代码块
    static {
        try {
            initSigar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //初始化sigar的配置文件
    public static void initSigar() throws IOException {
        SigarLoader loader = new SigarLoader(Sigar.class);
        String lib = null;
        try {
            lib = loader.getLibraryName();
            log.info("init sigar so文件 " + lib);
        } catch (ArchNotSupportedException var7) {
            log.error("initSigar() error:{}", var7.getMessage());
        }
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/sigar.so/" + lib);
        if (resource.exists()) {
            InputStream is = resource.getInputStream();
            //log.info("sigar.so 路径:" + System.getProperty("java.io.tmpdir") + "/sigar/so");
            File tempDir = new File(System.getProperty("java.io.tmpdir") + "/sigar/so");
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(new File(tempDir, lib), false));
            int lentgh = 0;
            while ((lentgh = is.read()) != -1) {
                os.write(lentgh);
            }
            is.close();
            os.close();
            System.setProperty("org.hyperic.sigar.path", tempDir.getCanonicalPath());
            log.info("org.hyperic.sigar.path:" + System.getProperty("org.hyperic.sigar.path"));
        }
    }
}

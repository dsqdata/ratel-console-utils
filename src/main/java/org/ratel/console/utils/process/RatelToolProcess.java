package org.ratel.console.utils.process;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public abstract class RatelToolProcess {

    abstract public void process() throws Exception;

    public static String getScannerInput(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        String scannerString = scanner.next();
        return scannerString;
    }

}

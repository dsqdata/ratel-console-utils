package org.ratel.console.utils.base;

import java.util.Scanner;

public abstract class RatelBaseProcess {

    abstract public void beforeProcess() throws Exception;

    abstract public void processOperation() throws Exception;

    abstract public void process() throws Exception;

    public static String getScannerInput(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        String scannerString = scanner.next();
        return scannerString;
    }
}

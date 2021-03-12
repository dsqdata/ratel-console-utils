package org.ratel.console.utils.process;

import java.util.Scanner;

public abstract class RatelToolProcess {

    abstract public void beforeProcess() throws Exception;
    abstract public void processOperation() throws Exception;


    public void process() throws Exception {
        beforeProcess();
        processOperation();
    }


    public static String getScannerInput(String title) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(title);
        String scannerString = scanner.next();
        return scannerString;
    }

}

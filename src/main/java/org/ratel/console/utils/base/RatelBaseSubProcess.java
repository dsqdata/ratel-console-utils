package org.ratel.console.utils.base;

import java.util.Scanner;

public abstract class RatelBaseSubProcess extends RatelBaseProcess {

    public void beforeProcess() throws Exception {

    }

    public void process() throws Exception{
        processOperation();
    }

}

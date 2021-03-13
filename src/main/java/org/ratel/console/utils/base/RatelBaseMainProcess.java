package org.ratel.console.utils.base;

import java.util.Scanner;

public abstract class RatelBaseMainProcess extends RatelBaseProcess {

    public void process() throws Exception {
        beforeProcess();
        processOperation();
    }

}

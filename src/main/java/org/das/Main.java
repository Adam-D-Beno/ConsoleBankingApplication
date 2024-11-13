package org.das;

import org.das.service.oparations.OperationsConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");
        OperationsConsoleListener operationsConsoleListener = context.getBean(OperationsConsoleListener.class);
        operationsConsoleListener.run();
    }
}

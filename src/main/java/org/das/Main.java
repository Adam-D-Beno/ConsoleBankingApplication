package org.das;

import org.das.service.OperationsConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");
        context.getBean(OperationsConsoleListener.class).run();
    }
}

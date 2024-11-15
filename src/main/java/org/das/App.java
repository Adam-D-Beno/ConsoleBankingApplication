package org.das;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");
        context.getBean(OperationsConsoleListener.class).listenUpdates();
    }
}

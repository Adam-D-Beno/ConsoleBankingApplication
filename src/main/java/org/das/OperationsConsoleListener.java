package org.das;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.annotation.Annotation;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class OperationsConsoleListener {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.das");

        while (true) {
            System.out.println("-ACCOUNT_CREATE\n" +
                    "-SHOW_ALL_USERS\n" +
                    "-ACCOUNT_CLOSE\n" +
                    "-ACCOUNT_WITHDRAW\n" +
                    "-ACCOUNT_DEPOSIT\n" +
                    "-ACCOUNT_TRANSFER\n" +
                    "-USER_CREATE");

        }
    }
}
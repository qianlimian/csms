package com.bycc;

import com.bycc.entity.Case;
import com.bycc.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class App {

    @Autowired
    private CaseService service;


    public static void main(String[] args) {

        System.out.println("Hello World!");

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        App app = context.getBean(App.class);

        app.test();
    }


    public void test() {
        final List<Case> cases = service.findAll();

        if (cases.size() == 0) {
            System.out.println("There are no cases.");
            return;
        }

        System.out.println("Persons:");
        for (Case caze : cases) {
            System.out.println(caze);
        }
    }
}

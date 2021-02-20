package com.example;

import com.example.facade.ISchool;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 2、(必做)写代码实现 Spring Bean 的装配，方式越多越好(XML、Annotation 都可以), 提交到 Github。
 */
public class SpringBeanLoad {
    
    public static void main(String[] args) {
        //方法1 xml
        solution1();

        //方法2 @Component
        solution2();

        //方法三 @Configuration
        solution3();
    }

    private static void solution1() {
        System.out.println("solution1");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ISchool school = context.getBean(ISchool.class);
        school.ding();
    }

    private static void solution2() {
        System.out.println("solution2");
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanScan.class);
        ISchool school = context.getBean(ISchool.class);
        school.ding();
    }

    private static void solution3() {
        System.out.println("solution3");
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfigration.class);
        ISchool school = context.getBean(ISchool.class);
        school.ding();
    }
}

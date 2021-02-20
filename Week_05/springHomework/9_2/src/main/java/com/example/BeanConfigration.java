package com.example;
import com.example.beans.Klass;
import com.example.beans.School;
import com.example.beans.Student;

import com.example.facade.ISchool;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigration {
    @Bean
    public Student student100() {
        return new Student(300, "solution3");
    }

    @Bean
    public Klass class1(@Qualifier("student100") Student student100){
        Klass klass = new Klass();
        klass.setStudents(Lists.newArrayList(student100));
        return klass;
    }

    @Bean
    public ISchool school(@Qualifier("student100") Student student100, @Qualifier("class1") Klass klass) {
        final School school = new School();
        school.setClass1(klass);
        school.setStudent100(student100);
        return school;
    }
}

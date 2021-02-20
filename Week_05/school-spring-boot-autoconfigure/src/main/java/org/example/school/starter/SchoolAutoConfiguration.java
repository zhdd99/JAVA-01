package org.example.school.starter;

import java.util.Arrays;

import org.example.school.starter.beans.Klass;
import org.example.school.starter.beans.School;
import org.example.school.starter.beans.Student;
import org.example.school.starter.facade.ISchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(SchoolProperties.class)
@Configuration
public class SchoolAutoConfiguration {

    @Autowired
    private SchoolProperties schoolProperties;

    @Bean
    public Student student100() {
        return new Student(schoolProperties.getStudentId(), schoolProperties.getStudentName());
    }

    @Bean
    public Klass class1(@Qualifier("student100") Student student100){
        Klass klass = new Klass();
        klass.setStudents(Arrays.asList(student100));
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

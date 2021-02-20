package org.example.school.starter.beans;

import lombok.Data;
import org.example.school.starter.facade.ISchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Data
public class School implements ISchool {

    Klass class1;

    Student student100;

    @Override
    public void ding(){
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student100);
    }
    
}

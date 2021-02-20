package com.example.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
@Component("student100")
public class Student implements Serializable{

    private int id;
    private String name;

    public Student(){
        this.id = 200;
        this.name = "solution2";
    }


}

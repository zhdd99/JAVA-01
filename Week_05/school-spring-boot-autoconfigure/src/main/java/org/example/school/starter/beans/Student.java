package org.example.school.starter.beans;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class Student implements Serializable{

    private int id;
    private String name;

    public Student(){
        this.id = 200;
        this.name = "solution2";
    }


}

package com.example.starter.test;

import javax.annotation.PostConstruct;

import org.example.school.starter.facade.ISchool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchoolStarterTest {

    @Autowired
    private ISchool school;

    @PostConstruct
    public void testStarter(){
        school.ding();
    }
}

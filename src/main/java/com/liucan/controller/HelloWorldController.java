package com.liucan.controller;

import com.liucan.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 * @Description:
 */
@RestController
public class HelloWorldController {
    @Autowired
    private Person person;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World1";
    }

    @RequestMapping("/getPerson")
    public Person getPerson() {
        person.setAddress("sfsfs");
        person.setName("小明");
        return person;
    }
}


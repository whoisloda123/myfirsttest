package com.liucan.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author: liucan
 * @Date: 2018/7/6
 * @Description:
 */
@Component
@Data
public class Person {
    private String name; //姓名
    private Integer age; //年龄
    private String address; //家庭地址
}

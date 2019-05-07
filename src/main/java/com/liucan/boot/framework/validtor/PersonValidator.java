package com.liucan.boot.framework.validtor;

import com.liucan.boot.mode.Person;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author liucan
 * @date 2018/6/30
 * @brief Person局部检验器（在Controller里面添加@InitBinde）
 */
public class PersonValidator implements Validator {
    /**
     * 判断支持的JavaBean类型，此处支持Student类型
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    /**
     * 实现Validator中的validate接口进行校验
     */
    @Override
    public void validate(Object target, Errors errors) {
        //把校验信息注册到Error的实现类里
        if (target instanceof Person) {
            Person person = (Person) target;
            if (StringUtils.isEmpty(person.getAddress())) {
                errors.rejectValue("address", null, "地址不能为空!!!!");
            }
            if (person.getAge() < 18) {
                errors.rejectValue("age", null, "年龄未满18岁");
            }
            //此处可以实现其他自定义比较复杂的的校验
        }
    }
}

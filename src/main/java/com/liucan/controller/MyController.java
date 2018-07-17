package com.liucan.controller;

import com.liucan.common.validtor.PersonValidator;
import com.liucan.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author liucan
 * @date 2018/7/7
 * @brief
 */
@Controller
@RequestMapping("/bootlearn")
public class MyController {
    /**
     * spring-boot默认的资源模板路径resources/templates
     */
    @GetMapping(value = "/person")
    public ModelAndView person() {
        return new ModelAndView("person", "command", new Person());
    }

    @PostMapping(value = "/addPerson")
    public String addPerson(@ModelAttribute("person") @Valid Person person,
                            BindingResult bindingResult,
                            ModelMap model) {
        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                sb.append(error.getField() + ":").append(error.getDefaultMessage());
            }
            return sb.toString();
        } else {
            model.addAttribute("name", person.getName());
            model.addAttribute("age", person.getAge());
            model.addAttribute("address", person.getAddress());
            return "result";
        }
    }

    @InitBinder //在该control里面所有RequestMapping之前都会执行
    public void initBinder(WebDataBinder webDataBinder) {
        //添加PersonValidtor
        if (webDataBinder.getTarget() instanceof Person) {
            webDataBinder.addValidators(new PersonValidator());
        }
    }
}

package com.liucan.common.validtor;

import com.liucan.domain.Country;
import com.liucan.domain.World;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author liucan
 * @date 2018/7/1
 * @brief 全局验证器，配合@Valid使用
 * 2种方法：
 * 1.在类的属性上面添加类似于@Min注解，如在Student类上面加类似于@Min，简单方便
 * 2.重写Validator接口，在validate里面进行自定义校验，可以自己添加比较复杂的校验
 */
public class CommonValidator implements Validator {
    /**
     * 支持的JavaBean类型,可以添加自定义支持的类型
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Country.class.equals(clazz) ||
                World.class.equals(clazz);
    }

    /**
     * 实现Validator中的validate接口进行校验
     */
    @Override
    public void validate(Object target, Errors errors) {
        //把校验信息注册到Error的实现类里
        if (target instanceof Country) { //校验Country
            Country country = (Country) target;
            if (StringUtils.isEmpty(country.getName())) {
                errors.rejectValue("name", null, "名字不能为空!!!!");
            }
            //此处可以实现其他自定义比较复杂的的校验
        } else if (target instanceof World) { //校验World
            World world = (World) target;
            if (world.getCountry() == null) {
                errors.rejectValue("country", null, "country不能为空!!!!");
            }
        }
    }
}

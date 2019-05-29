package com.liucan.boot.service.dubbo;

import com.liucan.boot.service.db.UserInfoMybatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author liucan
 * @date 2018/8/5
 * @brief dubbo服务类
 */
//@Service(version = "1.1.0")
@Component
public class BootDubboServiceImpl implements IBootDubboService {
    @Autowired
    private UserInfoMybatisService userInfoMybatisService;

    @Override
    public String getUserName(Integer userId) {
        return userInfoMybatisService.getUserPhone(userId).toString();
    }
}

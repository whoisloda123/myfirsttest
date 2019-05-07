package com.liucan.boot.service;

import com.liucan.boot.web.common.CommonResponse;
import org.springframework.stereotype.Service;

/**
 * @author liucan
 * @date 2018/7/8
 * @brief
 */
@Service
public class UserInfoMybatisService {

    public CommonResponse getName(int userId) {
        return CommonResponse.error("未查询到");
    }

    public CommonResponse getUserPhone(int userId) {
        return CommonResponse.ok();
    }
}

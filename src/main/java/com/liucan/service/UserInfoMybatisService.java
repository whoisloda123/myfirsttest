package com.liucan.service;

import com.liucan.common.response.CommonResponse;
import com.liucan.mybatis.dao.DaoMapper;
import com.liucan.mybatis.dao.UserInfoMapper;
import com.liucan.mybatis.mode.UserInfo;
import com.liucan.mybatis.mode.UserInfoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liucan
 * @date 2018/7/8
 * @brief
 */
@Service
public class UserInfoMybatisService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private DaoMapper daoMapper;

    public CommonResponse getName(int userId) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return CommonResponse.ok(list.get(0).getUserName());
        } else {
            return CommonResponse.error("未查询到");
        }
    }

    public CommonResponse getUserPhone(int userId) {
        String phone = daoMapper.getUserPhone(String.valueOf(userId));
        return CommonResponse.ok(phone);
    }
}

package com.liucan.boot.persist.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liucan.boot.persist.mybatis.mode.CommonUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author liucan
 * @version 19-12-29
 */
public interface CommonUserMapper extends BaseMapper<CommonUser> {
    CommonUser selectByName(@Param("name") String name);
}

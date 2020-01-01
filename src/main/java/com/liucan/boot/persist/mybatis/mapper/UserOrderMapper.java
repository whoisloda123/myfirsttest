package com.liucan.boot.persist.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liucan.boot.persist.mybatis.mode.UserOrder;
import org.apache.ibatis.annotations.Param;

/**
 * @author liucan
 * @version 20-1-1
 */
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    IPage<UserOrder> selectPageVo(Page page, @Param("payType") Integer payType);
}

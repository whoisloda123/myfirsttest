package com.liucan.mybatis.dao;

import com.liucan.mybatis.mode.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liucan
 * @date 2018/5/13
 * @brief https://blog.csdn.net/abcd898989/article/details/51227545
 */
public interface DaoMapper {
    @Select("SELECT phone FROM user_info WHERE user_id = #{userId}")
    String getUserPhone(@Param("userId") String userId);

    String getUserEmail(@Param("userId") String userId,
                        @Param("phone") String phone);

    String getUserName(@Param("userId") String userId,
                       @Param("phone") String phone,
                       @Param("email") String email);

    void updateUser(@Param("userId") String userId,
                    @Param("phone") String phone,
                    @Param("email") String email);

    List<UserInfo> selectUserInfoInUids(@Param("uids") List<String> uids);
}

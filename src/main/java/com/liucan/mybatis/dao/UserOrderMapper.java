package com.liucan.mybatis.dao;

import com.liucan.mybatis.mode.UserOrder;
import com.liucan.mybatis.mode.UserOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserOrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    long countByExample(UserOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int deleteByExample(UserOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int insert(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int insertSelective(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    List<UserOrder> selectByExample(UserOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    UserOrder selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int updateByExampleSelective(@Param("record") UserOrder record, @Param("example") UserOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int updateByExample(@Param("record") UserOrder record, @Param("example") UserOrderExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int updateByPrimaryKeySelective(UserOrder record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_order
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    int updateByPrimaryKey(UserOrder record);
}
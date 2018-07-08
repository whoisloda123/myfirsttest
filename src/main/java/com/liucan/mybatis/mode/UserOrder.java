package com.liucan.mybatis.mode;

import java.util.Date;

public class UserOrder {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.id
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.user_id
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.order_id
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private String orderId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.order_stat
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Integer orderStat;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.price
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Integer price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.create_time
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_order.update_time
     *
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.id
     *
     * @return the value of user_order.id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.id
     *
     * @param id the value for user_order.id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.user_id
     *
     * @return the value of user_order.user_id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.user_id
     *
     * @param userId the value for user_order.user_id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.order_id
     *
     * @return the value of user_order.order_id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.order_id
     *
     * @param orderId the value for user_order.order_id
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.order_stat
     *
     * @return the value of user_order.order_stat
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Integer getOrderStat() {
        return orderStat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.order_stat
     *
     * @param orderStat the value for user_order.order_stat
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setOrderStat(Integer orderStat) {
        this.orderStat = orderStat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.price
     *
     * @return the value of user_order.price
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.price
     *
     * @param price the value for user_order.price
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.create_time
     *
     * @return the value of user_order.create_time
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.create_time
     *
     * @param createTime the value for user_order.create_time
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_order.update_time
     *
     * @return the value of user_order.update_time
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_order.update_time
     *
     * @param updateTime the value for user_order.update_time
     * @mbg.generated Sun Jul 08 15:08:14 CST 2018
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
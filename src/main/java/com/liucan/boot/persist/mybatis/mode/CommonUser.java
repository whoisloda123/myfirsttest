package com.liucan.boot.persist.mybatis.mode;

public class CommonUser {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column common_user.id
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column common_user.name
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column common_user.id
     *
     * @return the value of common_user.id
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column common_user.id
     *
     * @param id the value for common_user.id
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column common_user.name
     *
     * @return the value of common_user.name
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column common_user.name
     *
     * @param name the value for common_user.name
     *
     * @mbg.generated Wed Jan 01 17:50:36 CST 2020
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}
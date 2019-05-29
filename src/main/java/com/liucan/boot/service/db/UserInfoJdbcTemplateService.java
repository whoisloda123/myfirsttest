package com.liucan.boot.service.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author liucan
 * @date 2018/7/8
 * @brief 通过JdbcTemplate来操作数据库, Spring的JdbcTemplate是自动配置的，
 *        你可以直接使用@Autowired来注入到你自己的bean中来使用
 */
@Service
public class UserInfoJdbcTemplateService {
    //@Autowired
    private JdbcTemplate jdbcTemplate;

    public String queryUser(int userId) {
        String userName = jdbcTemplate.queryForObject("select user_name from user_info where user_id = ?",
                new Object[]{userId}, String.class);
        return userName;
    }
}

package com.liucan.boot.mode;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liucan
 * @version 20-1-1
 */
@Data
public class OrderQuery {
    private List<Integer> ids;
    private Integer userId;
    private String address;
    private Integer payType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}

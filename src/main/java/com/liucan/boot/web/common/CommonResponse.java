package com.liucan.boot.web.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liucan
 * @date 2018/6/3
 * @brief 通用返回response
 *        加Serializable，是因为在用spring cache redis时候保存到redis的对象需要支持序列化
 */
@Data
public class CommonResponse implements Serializable {
    private int code; //返回code
    private String message; //返回信息
    private Object data; //返回数据

    private CommonResponse() {

    }

    public static CommonResponse ok(Object data) {
        CommonResponse response = new CommonResponse();
        response.setCode(0);
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static CommonResponse ok() {
        CommonResponse response = new CommonResponse();
        response.setCode(0);
        response.setMessage("success");
        return response;
    }

    public static CommonResponse ok(int code, String message, Object data) {
        CommonResponse response = new CommonResponse();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static CommonResponse error(String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(-1);
        response.setMessage(message);
        return response;
    }

    public static CommonResponse error(int code, String message) {
        CommonResponse response = new CommonResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}

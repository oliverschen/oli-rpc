package com.github.oliverschen.olirpc.response;

import com.github.oliverschen.olirpc.exception.OliException;

/**
 * 标准返回题
 *
 * @author ck
 */
public class OliResp {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 数据
     */
    private Object data;

    /**
     * 消息
     */
    private String msg;

    /**
     * 异常
     */
    private OliException exception;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public OliException getException() {
        return exception;
    }

    public void setException(OliException exception) {
        this.exception = exception;
    }
}

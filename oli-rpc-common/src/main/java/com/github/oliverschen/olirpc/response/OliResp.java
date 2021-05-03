package com.github.oliverschen.olirpc.response;

/**
 * 标准返回题
 *
 * @author ck
 */
public class OliResp {

    public static OliResp ok(Object data) {
        OliResp resp = new OliResp();
        resp.setCode(200);
        resp.setData(data);
        resp.setException(null);
        resp.setMsg("success");
        return resp;
    }

    public static OliResp error(String msg, Exception e) {
        OliResp resp = new OliResp();
        resp.setCode(500);
        resp.setData(null);
        resp.setException(e);
        resp.setMsg(msg);
        return resp;
    }

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
    private Exception exception;

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

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}

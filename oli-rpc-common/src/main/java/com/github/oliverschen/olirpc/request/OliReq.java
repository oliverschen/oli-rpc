package com.github.oliverschen.olirpc.request;

/**
 * 标准请求
 *
 * @author ck
 */
public class OliReq {

    public OliReq() { }

    public OliReq(String service, String method, Object[] params) {
        this.service = service;
        this.method = method;
        this.params = params;
    }

    /**
     * 具体服务名「class」
     */
    private String service;

    /**
     * 服务具体方法名
     */
    private String method;

    /**
     * 服务方法参数
     */
    private Object[] params;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}

package com.github.oliverschen.olirpc.request;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 标准请求
 *
 * @author ck
 */
public class OliReq {

    public OliReq() {
        this.id = unionId.incrementAndGet();
    }

    AtomicLong unionId = new AtomicLong(0);

    public OliReq(String service, String method, Object[] params) {
        this.service = service;
        this.method = method;
        this.params = params;
        this.id = unionId.incrementAndGet();
    }

    /**
     * 唯一ID，用来绑定一次完整请求
     */
    private Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public String toString() {
        return "OliReq{" +
                "unionId=" + unionId +
                ", id=" + id +
                ", service='" + service + '\'' +
                ", method='" + method + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}

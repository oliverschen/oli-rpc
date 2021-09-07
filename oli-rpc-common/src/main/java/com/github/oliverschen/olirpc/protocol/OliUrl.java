package com.github.oliverschen.olirpc.protocol;

/**
 * 服务寻址协议
 * http://192.168.0.104:17828
 * @author ck
 */
public class OliUrl<T> {

    /**
     * 原始 url
     */
    private String srcUrl;

    private String host;
    private Integer port;

    /**
     * 协议：netty/http
     */
    private String protocol;

    /**
     * 限定名
     */
    private Class<T> serviceClass;

    /**
     * 代理类
     */
    private String proxy;

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Class<T> getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }
}

package com.github.oliverschen.olirpc;

import com.github.oliverschen.olirpc.protocol.OliUrl;

/**
 * 集群
 * @author ck
 */
public interface Cluster {

    /**
     * 协议转换
     * @return OliUrl
     * @param url 服务唯一路径
     * @param serviceClass 目标类
     */
    <T> OliUrl<T> obtainOliUrl(String url,Class<T> serviceClass);
}

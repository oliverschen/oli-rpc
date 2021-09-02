package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.annotaion.OliSPI;
import com.github.oliverschen.olirpc.protocol.OliUrl;

import static com.github.oliverschen.olirpc.constant.Constants.URL_CLUSTER;

/**
 * cluster 相关操作，暂时只包含获取 url
 * @author ck
 */
@OliSPI(URL_CLUSTER)
public interface Cluster {

    /**
     * 协议转换
     * @return OliUrl
     * @param url 服务唯一路径
     * @param serviceClass 目标类
     * @param protocol
     */
    <T> OliUrl<T> obtainOliUrl(String url, Class<T> serviceClass, String protocol);
}

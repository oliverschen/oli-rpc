package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.protocol.OliUrl;

/**
 * @author ck
 */
public class OliUrlCluster extends AbstractCluster {

    /**
     * 调用默认的父类方法
     */
    @Override
    public <T> OliUrl<T> obtainOliUrl(String url, Class<T> serviceClass, String protocol) {
        // todo
        return super.obtainOliUrl(url, serviceClass, protocol);
    }
}

package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.Cluster;
import com.github.oliverschen.olirpc.protocol.OliUrl;

/**
 * @author ck
 */
public abstract class AbstractCluster implements Cluster {

    @Override
    public  <T> OliUrl<T> obtainOliUrl(String url, Class<T> serviceClass, String protocol) {
        return doObtainOliUrl(url, serviceClass, protocol);
    }

    abstract <T> OliUrl<T> doObtainOliUrl(String url, Class<T> serviceClass, String protocol);

}

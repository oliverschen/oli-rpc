package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.protocol.OliUrl;
import org.springframework.stereotype.Component;

/**
 * @author ck
 */
@Component
public class OliUrlCluster extends AbstractCluster {

    /**
     * 调用默认的父类方法
     */
    @Override
    public <T> OliUrl<T> obtainOliUrl(String url,Class<T> serviceClass) {
        return super.obtainOliUrl(url, serviceClass);
    }
}

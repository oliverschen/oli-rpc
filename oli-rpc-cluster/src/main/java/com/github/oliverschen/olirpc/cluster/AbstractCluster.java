package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.Cluster;
import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import static com.github.oliverschen.olirpc.constant.Constants.*;

/**
 * @author ck
 */
public abstract class AbstractCluster implements Cluster {

    @Autowired
    private OliProperties oliProperties;

    @Override
    public  <T> OliUrl<T> obtainOliUrl(String url, Class<T> serviceClass) {
        OliUrl<T> oliUrl = new OliUrl<>();
        oliUrl.setSrcUrl(url);
        if (StringUtils.hasLength(url)) {
            String[] address = url.split(URL_SPLIT)[1].split(URL_COLON);
            if (address.length != SPLIT_SIZE) {
                throw new OliException("配置有误");
            }
            oliUrl.setHost(address[0]);
            oliUrl.setPort(Integer.parseInt(address[1]));
        }

        oliUrl.setProtocol(oliProperties.getProtocol());
        oliUrl.setServiceClass(serviceClass);
        return oliUrl;
    }

}

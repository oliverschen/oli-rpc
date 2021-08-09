package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.exception.OliException;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import org.springframework.util.StringUtils;

import static com.github.oliverschen.olirpc.constant.Constants.*;

/**
 * @author ck
 */
public class OliUrlCluster extends AbstractCluster {

    @Override
    <T> OliUrl<T> doObtainOliUrl(String url, Class<T> serviceClass, String protocol) {
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

        oliUrl.setProtocol(protocol);
        oliUrl.setServiceClass(serviceClass);
        return oliUrl;
    }
}

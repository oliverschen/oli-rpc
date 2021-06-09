package com.github.oliverschen.olirpc.cluster;

import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.protocol.OliUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.github.oliverschen.olirpc.constant.Constants.URL_COLON;
import static com.github.oliverschen.olirpc.constant.Constants.URL_SPLIT;

/**
 * @author ck
 */
@Component
public class OliUrlCluster extends AbstractCluster {

    @Autowired
    private OliProperties oliProperties;

    @Override
    public <T> OliUrl<T> obtainOliUrl(String url,Class<T> serviceClass) {
        OliUrl<T> oliUrl = new OliUrl<>();
        oliUrl.setSrcUrl(url);
        if (StringUtils.hasLength(url)) {
            String[] address = url.split(URL_SPLIT)[1].split(URL_COLON);
            oliUrl.setHost(address[0]);
            oliUrl.setPort(Integer.parseInt(address[1]));
        }

        oliUrl.setProtocol(oliProperties.getProtocol());
        oliUrl.setServiceClass(serviceClass);
        return oliUrl;
    }
}

package com.github.oliverschen.olirpc.remote.netty.event;

import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.properties.OliProperties;
import com.github.oliverschen.olirpc.remote.netty.server.OliNettyServer;
import com.github.oliverschen.olirpc.remote.tomcat.OliTomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import static com.github.oliverschen.olirpc.constant.Enums.RemoteType.NETTY;

/**
 * @author ck
 */
@Component
public class NettyBootEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(NettyBootEvent.class);

    @Autowired
    private OliProperties oliProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // 启动 rpc 服务
            Enums.RemoteType remoteType = Enums.RemoteType.of(oliProperties.getProtocol());
            if (NETTY.equals(remoteType)) {
                OliNettyServer.init().start();
                log.info("start netty server");
            }else {
                OliTomcat.start();
                log.info("start oli tomcat by event");
            }
        }
    }
}

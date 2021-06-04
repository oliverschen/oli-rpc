package com.github.oliverschen.olirpc.remote.netty.event;

import com.github.oliverschen.olirpc.remote.netty.server.OliNettyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author ck
 */
@Component
public class NettyBootEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(NettyBootEvent.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            // 启动 rpc 服务
            OliNettyServer.init().start();
            log.info("netty server boot started");
        }
    }
}

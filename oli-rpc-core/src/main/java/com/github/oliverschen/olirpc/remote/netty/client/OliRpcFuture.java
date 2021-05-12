package com.github.oliverschen.olirpc.remote.netty.client;

import com.github.oliverschen.olirpc.response.OliResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ck
 */
public class OliRpcFuture {
    private static final Logger log = LoggerFactory.getLogger(OliRpcFuture.class);

    private OliResp resp;
    private volatile boolean isSucceed = false;
    private final Object lock = new Object();

    protected static final Map<Long, OliRpcFuture> FUTURE = new ConcurrentHashMap<>();

    public OliRpcFuture() { }

    public void setResp(OliResp resp) {
        if (isSucceed) {
            return;
        }
        synchronized (lock) {
            this.resp = resp;
            this.isSucceed = true;
            lock.notifyAll();
        }
    }

    public OliResp getResp() {
        log.info("invoke netty server resp() method");
        synchronized (lock) {
            while (!isSucceed) {
                try {
                    log.info("wait netty server result come back");
                    lock.wait(5000);
                } catch (InterruptedException e) {
                    log.error("get response error:", e);
                    Thread.currentThread().interrupt();
                }
            }
            return resp;
        }
    }
}

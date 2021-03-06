package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.loadbalance.LoadBalance;
import com.github.oliverschen.olirpc.router.Router;
import com.github.oliverschen.olirpc.constant.Enums;
import com.github.oliverschen.olirpc.exception.OliException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ck
 */
public abstract class AbstractExtension implements Extension {
    /**
     * router 实现类集合
     */
    protected static final ConcurrentHashMap<String, Router> ROUTER = new ConcurrentHashMap<>();

    /**
     * loadBalance 实现类集合
     */
    protected static final ConcurrentHashMap<String, LoadBalance> LOAD_BALANCE = new ConcurrentHashMap<>();


    /**
     * 获取扩展
     * @param key 扩展 key 值
     * @param type 扩展类型
     * @return 具体的扩展
     */
    public static Object obtain(String key, Enums.ExtensionType type) {
        switch (type) {
            case ROUTER:
                return ROUTER.get(key);
            case LOAD_BALANCE:
                return LOAD_BALANCE.get(key);
            default:
                throw new OliException("obtain extension error,key:" + key + ",type:" + type);
        }
    }

}

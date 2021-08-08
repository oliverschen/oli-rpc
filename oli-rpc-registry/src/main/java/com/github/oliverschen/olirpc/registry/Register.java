package com.github.oliverschen.olirpc.registry;

import com.github.oliverschen.olirpc.annotaion.OliSPI;
import com.github.oliverschen.olirpc.properties.OliProperties;

import java.util.Set;

import static com.github.oliverschen.olirpc.constant.Constants.REDIS_REGISTER;

/**
 * @author ck
 */
@OliSPI(REDIS_REGISTER)
public interface Register {

    /**
     * 注册服务到注册中心
     * @param bean 要注册的服务
     */
    void register(Object bean);

    /**
     * 获取 serviceKey 对应的服务列表
     *
     * @param serviceKey 注册中心 key
     * @return Set<Object> 多个实例上的 bean
     */
    Set<String> obtainServices(String serviceKey);

    /**
     * 创建客户端
     */
    Register newClient(OliProperties oliProperties);

}

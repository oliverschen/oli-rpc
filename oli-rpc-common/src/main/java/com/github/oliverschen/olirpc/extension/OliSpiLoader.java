package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.annotaion.OliSPI;
import com.github.oliverschen.olirpc.exception.OliException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 参考 https://dubbo.apache.org/zh/blog/2019/04/25/dubbo可扩展机制实战/
 * 学习 dubbo ExtentionLoader 机制
 * 1. 通过接口获取到对应的组件的 loader
 * 2. 通过组件的名称，用 loader 获取到具体的组件。
 * @author ck
 */
public class OliSpiLoader<T> {
    private static Logger log = LoggerFactory.getLogger(OliSpiLoader.class);


    private Class<T> instance;
    private static final ConcurrentMap<Class<?>, OliSpiLoader<?>> SPI_MAP = new ConcurrentHashMap<>();

    public OliSpiLoader(Class<T> instance) {
        this.instance = instance;
    }
    /**
     * 通过扩展点接口获取扩展点的 loader
     * @param spiInterface 扩展点
     */
    public static <T> OliSpiLoader<T> getSpiLoader(Class<T> spiInterface) {
        if (Objects.isNull(spiInterface)) {
            throw new OliException("spi interface is null");
        }
        if (!spiInterface.isInterface()) {
            throw new OliException(spiInterface + "is not a interface");
        }
        if (Objects.isNull(spiInterface.getAnnotation(OliSPI.class))) {
            throw new OliException(spiInterface + "is not a extension，checkout @OliSPI annotation");
        }
        @SuppressWarnings("unchecked")
        OliSpiLoader<T> oliSpiLoader = (OliSpiLoader<T>) SPI_MAP.get(spiInterface);
        if (Objects.isNull(oliSpiLoader)) {
            log.debug("init spi extension:{}", spiInterface);
            SPI_MAP.putIfAbsent(spiInterface, new OliSpiLoader<>(spiInterface));
            oliSpiLoader = (OliSpiLoader<T>) SPI_MAP.get(spiInterface);
        }
        return oliSpiLoader;
    }


    /**
     * 通过扩展点配置的名称获取具体的组件类
     * @param name 组件名称
     */
    public T getComponent(String name) {

        return null;
    }







}

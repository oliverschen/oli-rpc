package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.annotaion.OliSPI;
import com.github.oliverschen.olirpc.exception.OliException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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
    private static final Logger log = LoggerFactory.getLogger(OliSpiLoader.class);


    private Class<T> spiInterface;
    /**
     * 缓存扩展点，也就是标记了 @OliSPI 注解的接口
     * Class<?> : 接口
     */
    private static final ConcurrentMap<Class<?>, OliSpiLoader<?>> SPI_MAP = new ConcurrentHashMap<>();

    /**
     * META-INF/oli 文件夹下面 spiInterface 对应路径文件中的 key value 值，value 是经过 instance 之后的具体对象
     * Class<?> : 接口的实现，具体的实例化对象
     * String : spiInterface 对应路径文件中的 key 值
     */
    private static final ConcurrentMap<String, Object> SPI_INSTANCE = new ConcurrentHashMap<>();

    public OliSpiLoader(Class<T> spiInterface) {
        this.spiInterface = spiInterface;
    }
    /**
     * 通过扩展点接口获取扩展点的 loader
     * @param spiInterface 扩展点
     */
    @SuppressWarnings("unchecked")
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
     * @param instanceKey 组件名称 key 值，对应的 spiInterface 路径中
     */
    public T getSpiInstance(String instanceKey) {
        if (!StringUtils.hasLength(instanceKey)) {
            throw new OliException("spi instance name is empty.");
        }
        T instance = (T) SPI_INSTANCE.get(instanceKey);
        // 没有获取到要创建新的
        if (Objects.isNull(instance)) {
            instance = createInstance(instanceKey);
            SPI_INSTANCE.putIfAbsent(instanceKey, instance);
        }
        return instance;
    }

    /**
     * 寻找当前 spiInterface 文件中 key 对应的实例全限定名
     * @param instanceKey 实例全限定名
     * @return
     */
    private T createInstance(String instanceKey) {

        return null;
    }


}

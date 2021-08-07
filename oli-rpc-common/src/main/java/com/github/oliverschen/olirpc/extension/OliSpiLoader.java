package com.github.oliverschen.olirpc.extension;

import com.github.oliverschen.olirpc.annotaion.OliSPI;
import com.github.oliverschen.olirpc.exception.OliException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 参考 https://dubbo.apache.org/zh/blog/2019/04/25/dubbo可扩展机制实战/
 * 学习 dubbo ExtentionLoader 机制
 * 1. 通过接口获取到对应的组件的 loader
 * 2. 通过组件的名称，用 loader 获取到具体的组件。
 * @author ck
 */
public class OliSpiLoader<T> {
    private static final Logger log = LoggerFactory.getLogger(OliSpiLoader.class);

    private static final String BASE_PATH = "META-INF/oli/";

    /**
     * 当前扩展点的接口，用来加载 META-INF/oli 文件夹
     */
    private final Class<T> spiInterface;

    /**
     * 缓存扩展点，也就是标记了 @OliSPI 注解的接口
     * Class<?> : 接口
     */
    private static final ConcurrentMap<Class<?>, OliSpiLoader<?>> SPI_MAP = new ConcurrentHashMap<>();

    /**
     * META-INF/oli 文件夹下面 spiInterface 对应路径文件
     * Class<?> : 扩展点
     * Map<String,Object>> : 扩展点对应的所有值
     *  String ： 组件名称
     *  Object :  组件实例对象
     */
    private static final ConcurrentMap<Class<?>, Map<String,Object>> SPI_INSTANCE = new ConcurrentHashMap<>();

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

    public static void main(String[] args) {
//        OliSpiLoader.getSpiLoader()
    }

    /**
     * 通过扩展点配置的名称获取具体的组件类
     * @param instanceKey 组件名称 key 值，对应的 spiInterface 路径中
     */
    public T getSpiInstance(String instanceKey) {
        // 扩展点对应的所有值
        Map<String, Object> stringObjectMap = SPI_INSTANCE.get(spiInterface);
        if (!CollectionUtils.isEmpty(stringObjectMap)) {
            T instance = (T) stringObjectMap.get(instanceKey);
            if (Objects.nonNull(instance)) {
                return instance;
            }
        }
        stringObjectMap = initInstanceMap();
        // 没有获取到要创建新的
        loadResource(stringObjectMap);
        return (T) stringObjectMap.get(instanceKey);
    }

    private HashMap<String, Object> initInstanceMap() {
        return new HashMap<>(16);
    }


    private void loadResource(Map<String, Object> stringObjectMap) {
        String filePath = BASE_PATH + spiInterface.getName();
        ClassLoader classLoader = OliSpiLoader.class.getClassLoader();
        URL url = classLoader.getResource(filePath);
        if (Objects.isNull(url)) {
            throw new OliException("resource path error : " + filePath);
        }
        doLoadResource(url, classLoader,stringObjectMap);
    }

    private void doLoadResource(URL url, ClassLoader classLoader, Map<String, Object> stringObjectMap) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))) {            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] split = line.split("=");
                    String key = split[0].trim();
                    String classPath = split[1].trim();
                    Object o = doCreateInstance(classPath,classLoader);
                    stringObjectMap.putIfAbsent(key, o);
                }
            }
        } catch (IOException e) {
            log.error("do load resource error:", e);
        }
        SPI_INSTANCE.putIfAbsent(spiInterface, stringObjectMap);
    }

    /**
     * 创建对象
     */
    private Object doCreateInstance(String classPath, ClassLoader classLoader) {
        try {
            Class<?> aClass = classLoader.loadClass(classPath);
            return aClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            log.error("load class error", e);
        }
        throw new OliException("load class error");
    }


}

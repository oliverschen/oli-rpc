package com.github.oliverschen.olirpc.extension;

/**
 * 参考 https://dubbo.apache.org/zh/blog/2019/04/25/dubbo可扩展机制实战/
 * 学习 dubbo ExtentionLoader 机制
 * 1. 通过接口获取到对应的组件的 loader
 * 2. 通过组件的名称，用 loader 获取到具体的组件。
 * @author ck
 */
public class OliSpiLoader<T> {


    /**
     * 通过扩展点接口获取扩展点的 loader
     * @param spiInterface 扩展点
     */
    public static  <T> OliSpiLoader<T> getSpiLoader(Class<T> spiInterface) {

        return null;
    }


    /**
     * 通过扩展点配置的名称获取具体的组件类
     * @param name 组件名称
     */
    public T getComponent(String name) {

        return null;
    }







}

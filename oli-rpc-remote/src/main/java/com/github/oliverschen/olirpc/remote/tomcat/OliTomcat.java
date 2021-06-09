package com.github.oliverschen.olirpc.remote.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static com.github.oliverschen.olirpc.constant.Constants.NETTY_SERVER_DEFAULT_PORT;
import static com.github.oliverschen.olirpc.constant.Constants.OLI_TOMCAT_PATH;

/**
 * @author ck
 */
public class OliTomcat {
    private static final Logger log = LoggerFactory.getLogger(OliTomcat.class);

    public static void start() {
        log.info("oli tomcat starting");
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(NETTY_SERVER_DEFAULT_PORT);

        File path = new File(System.getProperty("user.dir"));
        StandardContext sc = (StandardContext) tomcat.addWebapp(OLI_TOMCAT_PATH, path.getAbsolutePath());
        WebResourceRoot resourceRoot = new StandardRoot(sc);
        // webapps
        DirResourceSet dirResourceSet = new DirResourceSet();
        // java字节码文件路径
        dirResourceSet.setBase(new File("target/classes").getAbsolutePath());
        // tomcat的默认写法 /WEB-INF/classes web项目的默认
        dirResourceSet.setWebAppMount("/WEB-INF/classes");
        // 这个项目文件夹的上一级目录，在tomcat中就是webapps
        dirResourceSet.setRoot(resourceRoot);
        dirResourceSet.setInternalPath(OLI_TOMCAT_PATH);
        // 相当于把项目设置到webapp中
        resourceRoot.addPreResources(dirResourceSet);
        sc.setReloadable(false);
        // 一个web项目
        sc.setResources(resourceRoot);
        try {
            tomcat.start();
        } catch (LifecycleException e) {
            log.error("oli tomcat start error", e);
        }
        log.info("oli tomcat started");
        tomcat.getServer().await();
    }
}

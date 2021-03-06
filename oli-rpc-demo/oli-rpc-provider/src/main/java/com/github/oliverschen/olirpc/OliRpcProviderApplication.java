package com.github.oliverschen.olirpc;

import com.github.oliverschen.olirpc.annotaion.EnableOliRpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableOliRpc(basePackage = "com.github.oliverschen.olirpc")
@SpringBootApplication
public class OliRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliRpcProviderApplication.class, args);
    }

}

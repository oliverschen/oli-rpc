package com.github.oliverschen.olirpc;

import com.github.oliverschen.olirpc.annotaion.OliRpcScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OliRpcScan(basePackage = "com.github.oliverschen.olirpc")
@SpringBootApplication
public class OliRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliRpcConsumerApplication.class, args);
    }

}

package com.github.oliverschen;

import com.github.oliverschen.olirpc.invoker.OliInvoker;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class OliRpcProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliRpcProxyApplication.class, args);
    }

    @PostMapping("/")
    public OliResp service(@RequestBody OliReq oliReq) {
        return OliInvoker.invoke(oliReq);
    }
}

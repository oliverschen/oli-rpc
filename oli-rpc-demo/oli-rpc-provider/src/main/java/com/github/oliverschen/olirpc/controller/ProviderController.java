package com.github.oliverschen.olirpc.controller;

import com.github.oliverschen.olirpc.remote.invoker.OliInvoker;
import com.github.oliverschen.olirpc.protocol.OliReq;
import com.github.oliverschen.olirpc.protocol.OliResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ck
 */
@RestController
public class ProviderController {

    @PostMapping("/")
    public OliResp register(@RequestBody OliReq req) {
        return OliInvoker.invoke(req);
    }
}

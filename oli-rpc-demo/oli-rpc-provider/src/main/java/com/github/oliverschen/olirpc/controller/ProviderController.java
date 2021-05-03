package com.github.oliverschen.olirpc.controller;

import com.github.oliverschen.olirpc.invoker.OliInvoker;
import com.github.oliverschen.olirpc.request.OliReq;
import com.github.oliverschen.olirpc.response.OliResp;
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
        OliResp invoke = OliInvoker.invoke(req);
        return invoke;
    }
}

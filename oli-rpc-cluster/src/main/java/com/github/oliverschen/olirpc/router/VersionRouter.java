package com.github.oliverschen.olirpc.router;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 按照 version 分组服务
 *
 * @author ck
 */
@Component("version")
public class VersionRouter extends AbstractRouter {

    @Override
    public List<String> route(Set<String> services) {
        // todo 用 version 来区分和鉴别服务
        return new ArrayList<>(services);
    }
}

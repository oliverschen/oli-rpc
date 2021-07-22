package com.github.oliverschen.olirpc.router;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 处理带有 tag 的服务
 *
 * @author ck
 */
@Component("tag")
public class TagRouter extends AbstractRouter {

    @Override
    public List<String> route(Set<String> services) {
        return new ArrayList<>(services);
    }
}

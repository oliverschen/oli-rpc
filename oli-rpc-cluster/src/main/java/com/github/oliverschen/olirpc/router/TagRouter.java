package com.github.oliverschen.olirpc.router;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 按照 tag 分组服务
 *
 * @author ck
 */
public class TagRouter extends AbstractRouter {

    @Override
    public List<String> route(Set<String> services) {
        // todo 用 tag 来鉴别分组服务
        return new ArrayList<>(services);
    }
}

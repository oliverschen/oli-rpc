package com.github.oliverschen.olirpc.router;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 按照 group 分组
 * @author ck
 */
@Component("group")
public class GroupRouter extends AbstractRouter {

    @Override
    public List<String> route(Set<String> services) {
        // 用 group 来鉴别分组服务
        return new ArrayList<>(services);
    }
}

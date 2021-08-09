package com.github.oliverschen.olirpc.router;

import com.github.oliverschen.olirpc.Router;

import java.util.List;
import java.util.Set;

/**
 * @author ck
 */
public abstract class AbstractRouter implements Router {

    @Override
    public List<String> route(Set<String> services) {
        return doRoute(services);
    }

    abstract List<String> doRoute(Set<String> services);
}

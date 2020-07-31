package com.ctd.springboot.nacos.route.repository;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.Executor;

/**
 * NacosRouteDefinitionRepository
 *
 * @author chentudong
 * @date 2020/7/31 11:17
 * @since 1.0
 */
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final String SCG_DATA_ID = "scg-routes";
    private static final String SCG_GROUP_ID = "SCG_GATEWAY";
    private final Map<String, RouteDefinition> routes = Collections.synchronizedMap(new LinkedHashMap<>());

    private final ApplicationEventPublisher publisher;
    private final NacosConfigManager nacosConfigManager;

    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigManager nacosConfigManager) {
        this.publisher = publisher;
        this.nacosConfigManager = nacosConfigManager;
        addListener();
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            List<RouteDefinition> routeDefinitions = getRouteDefinition(nacosConfigManager.getConfigService()
                    .getConfig(SCG_DATA_ID, SCG_GROUP_ID, 5000));
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        return Flux.fromIterable(CollUtil.newArrayList());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap((r) -> {
            this.routes.put(r.getId(), r);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap((id) -> {
            if (this.routes.containsKey(id)) {
                this.routes.remove(id);
                return Mono.empty();
            } else {
                return Mono.defer(() -> {
                    return Mono.error(new NotFoundException("RouteDefinition not found: " + routeId));
                });
            }
        });
    }

    /**
     * 添加监听器
     */
    private void addListener() {
        try {
            nacosConfigManager.getConfigService().addListener(SCG_DATA_ID, SCG_GROUP_ID, new Listener() {

                /**
                 * Get executor for execute this receive
                 *
                 * @return Executor
                 */
                @Override
                public Executor getExecutor() {
                    return null;
                }

                /**
                 * Receive config info
                 *
                 * @param configInfo config info
                 */
                @Override
                public void receiveConfigInfo(String configInfo) {
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    private List<RouteDefinition> getRouteDefinition(String config) {
        if (StringUtils.isBlank(config)) {
            return new ArrayList<>(0);
        }
        return JSON.parseArray(config, RouteDefinition.class);
    }
}

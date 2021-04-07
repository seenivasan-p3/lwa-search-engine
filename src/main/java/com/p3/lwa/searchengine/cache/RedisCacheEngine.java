package com.p3.lwa.searchengine.cache;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

@Component
public class RedisCacheEngine implements Condition {


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String cacheName = context.getEnvironment().getProperty("lwa.system.cache.method");
        return cacheName.equalsIgnoreCase("redis");
    }
}

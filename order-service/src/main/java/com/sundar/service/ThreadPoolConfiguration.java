package com.sundar.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfiguration {
     
    @Value("${bake.oven.size}")
    private int ovenSize;
     
    @Bean(name="asyncExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(ovenSize);
        pool.setMaxPoolSize(ovenSize);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.setThreadNamePrefix("bakingThread-");
        pool.initialize();
        return pool;
    }
}
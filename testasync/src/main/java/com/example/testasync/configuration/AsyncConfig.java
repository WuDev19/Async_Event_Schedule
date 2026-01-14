package com.example.testasync.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("asyncExecutor")
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor(); //của spring boot
        executor.setCorePoolSize(5); //thường trực có 5 thread
        executor.setMaxPoolSize(20); //nếu vượt quá 5 thread thì thêm thread đến max là 20
        executor.setQueueCapacity(100); //số lượng task tối đa trong queue
        executor.setThreadNamePrefix("async-");
        executor.initialize();
        return executor;
    }

}

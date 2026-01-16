package com.example.testasync.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanUpSchedule {

    @Scheduled(fixedRate = 2000, initialDelay = 0)
    public void cleanUp(){
        System.out.println("Clean Up");
        System.out.println(Thread.currentThread().getName());
    }

}

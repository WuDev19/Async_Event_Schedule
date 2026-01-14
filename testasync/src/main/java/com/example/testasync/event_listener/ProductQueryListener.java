package com.example.testasync.event_listener;

import com.example.testasync.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ProductQueryListener {

    @Autowired
    private LogService logService;

    @Async("asyncExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void listener(ProductQueryEvent event){
        logService.log(event.getProduct());
    }

}

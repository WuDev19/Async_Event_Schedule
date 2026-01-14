package com.example.testasync.service;

import com.example.testasync.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    public void log(List<Product> list){
        System.out.println("Bạn vừa query động với specification" + list);
    }

}

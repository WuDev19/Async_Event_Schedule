package com.example.testasync.event_listener.event;

import com.example.testasync.entity.Product;

import java.util.List;

public class ProductQueryEvent {
    private final List<Product> product;

    public ProductQueryEvent(List<Product> product) {
        this.product = product;
    }

    public List<Product> getProduct() {
        return product;
    }

}

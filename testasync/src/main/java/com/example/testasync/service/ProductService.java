package com.example.testasync.service;

import com.example.testasync.entity.Product;
import com.example.testasync.event_listener.ProductQueryEvent;
import com.example.testasync.repository.ProductRepository;
import com.example.testasync.specification.SpecificationProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public List<Product> get(Map<String, Object> map) {
        Specification<Product> sp = Specification.where((root, query, cb) ->
                cb.conjunction());
        if (map.get("shopName") != null) {
            sp = sp.and(SpecificationProduct.hasShopName((String) map.get("shopName")));
        }
        if (map.get("description") != null) {
            sp = sp.and(SpecificationProduct.hasDescription((String) map.get("description")));
        }
        if (map.get("discount") != null) {
            sp = sp.and(SpecificationProduct.hasDiscount(Integer.parseInt((String)map.get("discount"))));
        }
        List<Product> list = productRepository.findAll(sp);
        publisher.publishEvent(new ProductQueryEvent(list)); //phải trong transaction mới chạy được vì bên kia lắng nghe bằng @TransactionalEventListener
        return list;
    }

}

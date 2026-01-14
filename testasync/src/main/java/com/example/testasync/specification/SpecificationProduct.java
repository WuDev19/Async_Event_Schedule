package com.example.testasync.specification;

import com.example.testasync.entity.Product;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

@SuppressWarnings("ALL")
public class SpecificationProduct {

    public static Specification<Product> hasDescription(String des) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("motaSp"), "%" + des + "%");
    }

    public static Specification<Product> hasShopName(String shopName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("tenNguoiBan"), "%" + shopName + "%");
    }

    public static Specification<Product> hasDiscount(Integer discount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("giamgia"), discount);
    }

    //trường hợp join 2 bảng
    public static Specification<Product> join(String userName) {
        return (root, query, criteriaBuilder) -> {
            Join<Product, String> join = root.join("user");
            return criteriaBuilder.equal(join.get("username"), userName);
        };
    }

}

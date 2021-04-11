package com.jumanji.capston.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "buckets")
public class Bucket implements Serializable {
    @Id
//    @Column(insertable = false, updatable = false)
    private String id; // 바구니번호 yyyyMMddhhmmss

    @Column(length = 2)
    private int quantity; // 메뉴 수량
    @Column(name = "order_request")
    private String orderRequest; // 요청사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", updatable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public Bucket(String id, int quantity, String orderRequest, Shop shop, User user) {
        this.id = id;
        this.quantity = quantity;
        this.orderRequest = orderRequest;
        this.shop = shop;
        this.user = user;
    }

    @Getter @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int quantity;
        private String orderRequest;
        private String shopId;
        private String userId;
    }

    @Getter
    public static class Response{
        private String bucketId;
        private int quantity;
        private String orderRequest;
        private String shopId;
        private String userId;

        public Response(Bucket bucket) {
            this.bucketId = bucket.getId();
            this.quantity = bucket.quantity;
            this.orderRequest = bucket.getOrderRequest();
            this.shopId = bucket.getShop().getId();
            this.userId = bucket.getUser().getId();
        }
    }
}
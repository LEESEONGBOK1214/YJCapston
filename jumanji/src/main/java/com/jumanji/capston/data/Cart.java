package com.jumanji.capston.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "carts")
public class Cart implements Serializable {
    @Id
//    @Column(insertable = false, updatable = false)
    private Timestamp id; // 바구니번호 yyyyMMddhhmmss
    @Column(length = 2)
    private int people;
    @Column(name = "order_request")
    private String orderRequest; // 요청사항
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", updatable = false)
    private Shop shop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @Builder
    public Cart(Timestamp id, int people, String orderRequest, Shop shop, User user) {
        this.id = id;
        this.people = people;
        this.orderRequest = orderRequest;
        this.shop = shop;
        this.user = user;
    }

    @Getter @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private int people;
        private String orderRequest;
        private String shopId;
        private String userId;
    }

    @Getter
    public static class Response{
        private Timestamp cartId;
        private int people;
        private String orderRequest;
        private String shopId;
        private String userId;

        public Response(Cart cart) {
            this.cartId = cart.getId();
            this.people = cart.getPeople();
            this.orderRequest = cart.getOrderRequest();
            this.shopId = cart.getShop().getId();
            this.userId = cart.getUser().getId();
        }
    }
}
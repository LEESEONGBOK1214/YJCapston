package com.example.restapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name="reservation")
public class Reservation implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order_id; //아이디



    @Column(length = 30, nullable = false)
    private String pw; // 비밀번호
    @Column(length = 15, nullable = false)
    private String name; // 이름
    @Column(length = 70)
    private String email; // 이메일
    @Column(length = 120)
    private String address; // 주소
    @Column
    private Date birthday; // 생년월일
    @Column(length = 11, nullable = false)
    private String phone; // 전화번호
    @Column
    private char is_wdrw='N'; // 탈퇴여부
    @Column
    private String auth; // 권한
    @Column(length = 2)
    private String social; // 소셜
    @Column(updatable = false) // 업데이트, 인설트 불가
    private Date sign_date = new Date(); // 가입날짜
}

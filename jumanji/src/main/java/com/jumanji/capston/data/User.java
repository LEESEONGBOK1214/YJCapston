package com.jumanji.capston.data;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="user")
public class User {
    @Id
    @Column(length = 30)
    private String id; //아이디
    @Column(length = 100, nullable = false) // 암호화를 하는데 여유자리까지 충분하게 100자리.
    private String pw; // 비밀번호
    @Column(length = 15, nullable = false)
    private String name; // 이름
    @Column(length = 70)
    private String email; // 이메일
    @Column(length = 90)
    private String address; // 주소
    @Column(length = 90)
    private String address_detail;
    @Column
    private Date birthday; // 생년월일
    @Column(length = 11, nullable = false)
    private String phone; // 전화번호
    @Column
    private char is_wdrw; // 탈퇴여부
    @Column
    private String role; // 권한   u, o, a
    @Column(length = 2)
    private String provider; // 소셜
    @Column(insertable = false, updatable = false) // 업데이트, 인설트 불가
    private Date sign_date; // 가입날짜
    @Column(name="lv", length = 2)
    private String level; // 등급
    @Column(nullable = false)
    private int point; // 포인트

    private String provider_id;


    @Builder
    public User(String id, String pw, String name, String role, String email, Date sign_date, String provider, String provider_id, String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.email = email;
        this.role = role;
        this.sign_date = sign_date;
        this.provider = provider;
        this.provider_id = provider_id;
        this.phone = phone;
    }
//Test 용. Column 어노테이션 없어도 테이블에 추가 되는가?
    // Column 어노테이션이 없어도 잘 됨.
//    private Timestamp regTime;
//    @Column
//    private String token; // jwt.io 문서읽기
//    @Column
//    private int penalty; // 경고회수
}

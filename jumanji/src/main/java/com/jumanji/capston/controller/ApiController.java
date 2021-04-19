package com.jumanji.capston.controller;

import com.jumanji.capston.config.jwt.JwtTokenUtil;
import com.jumanji.capston.data.User;
import com.jumanji.capston.service.ShopServiceImpl;
import com.jumanji.capston.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    ShopServiceImpl shopService;
    @Autowired
    HttpHeaders httpHeaders;


//    @Autowired
//    private PrincipalDetailsService userDetailService;

    @Transactional // 트랜잭션화 시켜서 오류발생시 롤백이 되도록
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User.Request user) {
        System.out.println("회원가입 요청 ");
        System.out.println(user.toString());
        return userService.post(user);
    }

    @Transactional(readOnly = true) // 트랜잭션이긴 한데 읽기 전용으로 속도 업 !
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User.Request user) {
        System.out.println("/api/v1/login 요청");
        return userService.login(user);
    }


    @GetMapping("/validate/{id}") // validate
    public ResponseEntity<?> validateOne(@PathVariable String id) {
        return userService.validationId(id);
    }

//    @GetMapping("/ok")
//    public ResponseEntity<?> updateOk(){
//        return new ResponseEntity<>("update successfully", HttpStatus.OK);
//    }
}


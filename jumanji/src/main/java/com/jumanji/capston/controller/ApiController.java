package com.jumanji.capston.controller;

import com.jumanji.capston.Service.UserSerivce;
import com.jumanji.capston.controller.exception.MemberNotFoundException;
import com.jumanji.capston.data.User;
import com.jumanji.capston.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
//    UserRepository userRepository;
    UserSerivce userSerivce;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/join")
    public String join(@RequestBody User _user) {
        User user = _user;
        System.out.println("join\nm.toString() : " + user.toString() + "\n" +
                "m.getId() : " + user.getId() + "\n" +
                "m.getPw() : " + user.getPassword() + "\n" +
                "m.getName() : " + user.getName() + "\n" +
                "m.getPhone() : " + user.getPhone() + "\n" +
                "m.getRole() : " + user.getRole()
        );
        // 현재 비밀번호를 받는 족족 그대로 넣고있기 때문에 시큐리티에 걸려 로그인 불가능.
        // 비밀번호를 암호화 해서 넣어줘야 함.
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setSign_date(new Date());
        if(userSerivce.findById(user.getId()) != null)
            userSerivce.save(user);
        else{
            System.out.println("이미 있는 아이디. 회원가입 불가.");
        }

        return "/";
    }

    //spring security 설정 .loginProcessingUrl("/login") 으로 처리
//    @Transactional(readOnly = true)
//    @PostMapping("/login")
//    public String login(@ModelAttribute User user) {
//        System.out.println(">> login");
//        System.out.println("m.toString() : " + user.toString() + "\n"
//                + "m.getId() : " + user.getId() + "\n"
//                + "m.getPw() : " + user.getPw() + "\n"
////              + "m.getName() : " + user.getName() + "\n"
////              + "m.getPhone() : " + user.getPhone() + "\n"
////              + "m.getRole() : " + user.getRole()
//        );
////        return userRepository.findById(user.getId())
////                .orElseThrow(()-> new MemberNotFoundException(user.getId()));
//        return "login";
//    }

    @GetMapping("/userDelAll")
    public String memberDelAll(){
        userRepository.deleteAll();
        return "Member 전부 삭제.";
    }

//    @Transactional(readOnly = true)
    @GetMapping("/myInfo/{id}")
    public User myInfo(@PathVariable("i4d") String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new MemberNotFoundException(id));
    }

//    @Transactional(readOnly = true)
    @GetMapping("/userList")
    public List<User> Members() {
        List<User> memberList;
        memberList = userRepository.findAll();
        return memberList;
    }

    // user 권한만 접근 가능
    @GetMapping("/user")
    public String user(){
        return "user";
    }

    // admin 권한만 접근 가능
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    // owner 권한만 접근 가능
    @GetMapping("/owner")
    public String owner(){
        return "owner";
    }


    @GetMapping("/StatusCode")
    public ResponseEntity<?> statusCode(){
        int code = 200;
        if(code == 200)return new ResponseEntity<String>("ok", HttpStatus.OK);
        if(code == 404)return new ResponseEntity<String>("ok", HttpStatus.NOT_FOUND);
        if(code == 400)return new ResponseEntity<String>("ok", HttpStatus.BAD_REQUEST);
        if(code == 500)return new ResponseEntity<String>("ok", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

}

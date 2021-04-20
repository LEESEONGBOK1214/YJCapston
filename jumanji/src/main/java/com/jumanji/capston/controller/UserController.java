package com.jumanji.capston.controller;

import com.jumanji.capston.data.User;
import com.jumanji.capston.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class UserController  {
    @Autowired
    UserServiceImpl userService;


    @Transactional(readOnly = true)
    @GetMapping("/user")  // myInfo
    public ResponseEntity<?> selectMyInfo(@RequestHeader String authorization) {
        String loginId = userService.getMyId(authorization);
        User user = userService.get(loginId);
        User.Response response = new User.Response(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
//        return new ResponseEntity<>("is not match login id <-> request id.", HttpStatus.FORBIDDEN);
    }


    @Transactional(readOnly = true)
    @GetMapping("/userList") // getUserList
    public ResponseEntity<?> getUserList(@RequestHeader String authorization) {
        String loginId = userService.getMyId(authorization);
        String userRole = userService.get(loginId).getRole();
        userService.isAuth(userRole, "ADMIN");
        List<User.Response> response = new ArrayList<>();
        for (User _user : userService.getList(authorization)) {
            response.add(new User.Response(_user));
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/user/{id}")
    public ResponseEntity<?> selectUserInfo(@PathVariable String id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

//    @Transactional
//    @DeleteMapping("/userDelAll")
//    public ResponseEntity<?> userDelAll( @RequestHeader String authorization) {
//        return userService.deleteAll(authorization);
//    }

    @Transactional
    @PatchMapping("/user") // patch user
    public ResponseEntity<?> patchUser(@RequestBody User.Request request, @RequestHeader String authorization) {
        User.Response response = new User.Response(userService.patch(authorization, request));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/user") // delete user
    public ResponseEntity<?> deleteUser(@RequestHeader String authorization){
        userService.delete(authorization);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 삭제가 잘 되면 ok 반환.
    }
}
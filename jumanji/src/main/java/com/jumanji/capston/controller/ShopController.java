package com.jumanji.capston.controller;

import com.jumanji.capston.data.Request.ShopRequest;
import com.jumanji.capston.data.Shop;
import com.jumanji.capston.data.User;
import com.jumanji.capston.service.ShopService;
import com.jumanji.capston.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class ShopController {
//    Logger logger;


    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;

    @Autowired
    HttpHeaders httpHeaders;

    @Transactional(readOnly = true)
    @GetMapping("/shopList")
    public ResponseEntity<?> getShopList() {
        System.out.println("샵리스트 >> ");
        List<Shop> shopList = shopService.findAll();
        return new ResponseEntity<>(shopList, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/shop") // 매장등록
    public ResponseEntity<?> insertShop(@RequestBody ShopRequest shopRequest) throws ParseException {
        User userEntity = userService.findById(SecurityContextHolder.getContext().getAuthentication().getName());
//        System.out.println("매장등록 요청 ID : " + userEntity.getId());
//        logger.log(Level.INFO, "open time and close time\n" + shop.getOpenTime() +"\n" + shop.getCloseTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date openTime = null;
        Date closeTime = null;
        try {
            openTime = dateFormat.parse(shopRequest.getOpenTime());
            closeTime = dateFormat.parse(shopRequest.getCloseTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Shop shopEntity =
                Shop.builder()
                        .shopId(shopRequest.getShopId())
                        .name(shopRequest.getName())
                        .intro(shopRequest.getIntro())
                        .openTime(openTime)
                        .closeTime(closeTime)
                        .address(shopRequest.getAddress())
                        .addressDetail(shopRequest.getAddressDetail())
                        .category(shopRequest.getCategory())
                        .build();
        Object result = shopService.insert(shopEntity, userEntity);
        if (result.getClass() == Shop.class) return new ResponseEntity<>(result, HttpStatus.CREATED);
        else if (result.equals("duplicate"))
            return new ResponseEntity<>("사업자 번호가 중복입니다.", httpHeaders, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

//    @Transactional
//    @DeleteMapping("/shop")
//    public ResponseEntity<?> deleteShop(@RequestBody String id) {
//        return new ResponseEntity<>(shopService.delete(id), HttpStatus.OK);
//    }

//    @Transactional
//    @PutMapping("/shop")
//    public ResponseEntity<?> putShop(@RequestBody Shop shop){
//        return new ResponseEntity<>(shopService.insertShop(shop), HttpStatus.OK);
//    }





    @Transactional(readOnly = true)
    @GetMapping("/shop/{shopId}")
    public ResponseEntity<?> getShopById(@PathVariable String shopId){
        System.out.println("shop id : " + shopId);
        Shop shop = shopService.findById(shopId);
        if(shop == null)return new ResponseEntity<>("없는 매장번호", httpHeaders, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/myShop")
    public ResponseEntity<?> getMyShop() {
        User userEntity = userService.findById(SecurityContextHolder.getContext().getAuthentication().getName());
        if (userEntity == null) return new ResponseEntity<>("로그인 되어있지 않습니다.", httpHeaders, HttpStatus.BAD_REQUEST);
        System.out.println("접속 유저 ID : " + userEntity.getId());
        Object result = shopService.haveShop(userEntity.getId());
        if (result == null) return new ResponseEntity<>("매장 등록이 되어있지 않습니다.", httpHeaders, HttpStatus.BAD_REQUEST);
        if (result.getClass() == Shop.class) return new ResponseEntity<>(result, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Transactional(readOnly = true)
    @GetMapping("/shopIntro")
    public ResponseEntity<?> getShopIntro(@RequestBody ShopRequest shopRequest) {
        return new ResponseEntity<>(shopService.getShopIntro(shopRequest.getShopId()), httpHeaders, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/shopList/{category}")
    public ResponseEntity<?> getShopListByCategory(@PathVariable String category) {
        System.out.println("캣 : " + category);
        List<Shop> shopCatList = shopService.findByCat(category);
        if(shopCatList.size() != 0){
            System.out.println("샵캣리스트 :" + shopCatList.get(0));
            return new ResponseEntity<>(shopCatList, HttpStatus.OK);
        }
        return new ResponseEntity<>("매장이 없습니다.", httpHeaders, HttpStatus.OK);

//        return new ResponseEntity<>(shopCatList, httpHeaders, HttpStatus.OK); // 이렇게 하면 오류. 객체를 utf로 변환 시켜서 그런지 무슨 한글 변환하면서 오류나나봄!
    }
}

package com.jumanji.capston.controller;

import com.jumanji.capston.controller.commons.Controller;
import com.jumanji.capston.controller.exception.ApiErrorResponse;
import com.jumanji.capston.controller.exception.ShopException.ShopNotFoundException;
import com.jumanji.capston.data.Shop;
import com.jumanji.capston.service.ShopService;
import com.jumanji.capston.service.StorageService;
import com.jumanji.capston.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1")
public class ShopController extends Controller {
//    Logger logger;


    @Autowired
    ShopService shopService;

    @Autowired
    UserService userService;


    @Autowired
    StorageService storageService;


    @Transactional(readOnly = true)
    @GetMapping("/shop/{shopId}")  // get shop/{shopId} 식당번호로 식당 조회
    public ResponseEntity<?> getShopById(@PathVariable String shopId) {
        Shop shop;
        System.out.println("ShopController in getShopById");
        System.out.println("shop id : " + shopId);
        try {
            shop = shopService.findById(shopId);
        } catch (ShopNotFoundException e) {
            return new ResponseEntity<>(new ApiErrorResponse(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        Shop.Response response = new Shop.Response(shop);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/shopIntro/{shopId}")
    public ResponseEntity<?> getShopIntro(@PathVariable String shopId) {
        return shopService.getShopIntro(shopId);
    }

    @Transactional(readOnly = true)
    @GetMapping("/myShop")
    public ResponseEntity<?> getMyShop(@RequestHeader String authorization) { // 수정해야함.
        return userService.getMyShop(authorization);
    }

    @Transactional(readOnly = true)
    @GetMapping("/shopList")
    public ResponseEntity<?> selectShopList() {
        System.out.println("샵리스트 >> ");
        return shopService.findAll();
    }

    @Transactional(readOnly = true)
    @GetMapping("/shopList/{category}")
    public ResponseEntity<?> selectShopListByCategory(@PathVariable String category) {
        return shopService.findByCat(category);

//        return new ResponseEntity<>(shopCatList, httpHeaders, HttpStatus.OK); // 이렇게 하면 오류. 객체를 utf로 변환 시켜서 그런지 무슨 한글 변환하면서 오류나나봄!
    }

    @Transactional
    @PostMapping("/shop") // 매장등록     Form-data로 받음 => Param.
    public ResponseEntity<?> insertShop(Shop.info request, @RequestHeader String authorization) throws ParseException {
        return shopService.insert(request, authorization);
    }

    @Transactional
    @DeleteMapping("/shop/{shopId}")
    public ResponseEntity<?> deleteShop(@RequestHeader String authorization, @PathVariable String shopId) {
        return shopService.delete(authorization, shopId);
    }

    @Transactional
    @PutMapping("/shop")
    public ResponseEntity<?> putShop(@RequestHeader String authorization, @RequestBody Shop.Request shop) {
        return shopService.putShop(authorization, shop);
    }

    @Transactional
    @PatchMapping("/shop")
    public ResponseEntity<?> patchShop(@RequestHeader String authorization, @RequestBody Shop.Request request) {
        return shopService.patchShop(authorization, request);
    }

    @Transactional
    @PatchMapping("/shop/{shopId}/open")
    public ResponseEntity<?> updateShopIsOpen(@RequestHeader String authorization, @PathVariable String shopId) {
        return shopService.patchShopIsOpen(authorization, shopId);
    }


    //
    @Transactional
    @PatchMapping("/shop/{shopId}/reserve")
    public ResponseEntity<?> updateShopIsRsPos(@RequestHeader String authorization, @PathVariable String shopId) {
        return shopService.patchSHopIsRsPos(authorization, shopId);
    }

    private List<Shop> getMyShopList(String loginId) {
        return shopService.findByOwnerId(loginId);
    }


}

package com.jumanji.capston.repository;

import com.jumanji.capston.data.Order;
import com.jumanji.capston.data.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Timestamp> {

    List<Order> findALLByUser_Id(String user_Id);

    // 이성복 돼지???

    List<Order> findAllByShop_Id(String shopId);


    @Query(value = "select o.ID id,\n" +
            "       o.STATUS,\n" +
            "       o.ORDER_REQUEST orderRequest,\n" +
            "       o.PEOPLE,\n" +
            "       o.USE_POINT     usePoint,\n" +
            "       o.AMOUNT,\n" +
            "       o.ARRIVE_TIME   arriveTime,\n" +
            "       o.PAY_TIME      payTime,\n" +
            "       o.PG,\n" +
            "       o.PAY_METHOD    payMethod,\n" +
            "       o.ACCEPT,\n" +
            "       o.SHOP_ID       shopId,\n" +
            "       o.USER_ID       userId,\n" +
            "       o.REASON,\n" +
            "       case\n" +
            "           when r.id is not null then 'Y'\n" +
            "           else 'N' end\n" +
            "                     reviewed\n" +
            "from orders o\n" +
            "         left join REVIEWS R on o.ID = R.ORDER_ID\n" +
            "where o.USER_ID = :userId", nativeQuery = true)
//    @Query(value = "select orders.*,\n" +
//            "       case\n" +
//            "           when r.id is not null then 'Y'\n" +
//            "           else 'N' end\n" +
//            "                     reviewed\n" +
//            "from orders orders\n" +
//            "         left join REVIEWS R on orders.ID = R.ORDER_ID\n" +
//            "where orders.USER_ID = :userId", nativeQuery = true)
    List<Order.MyInfo> myOrderListContainsReviewed(String userId);


    @Query(value = "select \n" +
            "       case\n" +
            "           when o.USER_ID = :userId then 'Y'\n" +
            "           else 'N'\n" +
            "           end result\n" +
            "from ORDERS o\n" +
            "where id = :id", nativeQuery = true)
    char isMyOrder(String userId, Timestamp id);



}
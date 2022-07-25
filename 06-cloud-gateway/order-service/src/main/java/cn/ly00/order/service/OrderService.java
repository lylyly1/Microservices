package cn.ly00.order.service;

import cn.ly00.feign.clients.UserClient;
import cn.ly00.order.mapper.OrderMapper;
import cn.ly00.order.pojo.Order;
import cn.ly00.feign.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用RestTemplate发送http请求，查询用户信息
//        String url = "http://userservice/user/" + order.getUserId();
//        User user = restTemplate.getForObject(url, User.class);

        // Feign客户端发出请求
        User user = userClient.findById(order.getUserId());
        // 3.封装
        order.setUser(user);
        // 4.返回
        return order;
    }
}

package com.example.Bai2.Service;

import com.example.Bai2.Model.*;
import com.example.Bai2.Repository.OrderDetailRepository;
import com.example.Bai2.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Câu 7: Service tạo đơn hàng từ giỏ hàng session
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductService productService;

    /**
     * Tạo đơn hàng từ giỏ hàng (Map<productId, CartItem>)
     */
    public Order createOrder(String customerName, Map<Integer, CartItem> cart) {
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setOrderDate(LocalDateTime.now());

        // Tính tổng tiền
        double total = cart.values().stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        order.setTotalAmount(total);

        // Lưu order trước để có ID
        Order savedOrder = orderRepository.save(order);

        // Lưu từng OrderDetail
        List<OrderDetail> details = new ArrayList<>();
        for (CartItem item : cart.values()) {
            Product product = productService.getProductById(item.getProductId());
            if (product != null) {
                OrderDetail detail = new OrderDetail();
                detail.setOrder(savedOrder);
                detail.setProduct(product);
                detail.setQuantity(item.getQuantity());
                detail.setUnitPrice(item.getPrice());
                details.add(detail);
            }
        }
        orderDetailRepository.saveAll(details);
        savedOrder.setOrderDetails(details);

        return savedOrder;
    }
}

package com.example.Bai2.Controller;

import com.example.Bai2.Model.CartItem;
import com.example.Bai2.Model.Order;
import com.example.Bai2.Service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Câu 7: Đặt hàng (Checkout)
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /** Trang xác nhận đặt hàng */
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model, Authentication authentication) {
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }
        double total = cart.values().stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);
        String username = authentication != null ? authentication.getName() : "";
        model.addAttribute("username", username);
        return "checkout";
    }

    /** Xử lý đặt hàng */
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String customerName,
                                   HttpSession session,
                                   Model model) {
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.createOrder(customerName, cart);

        // Xóa giỏ hàng sau khi đặt hàng thành công
        session.removeAttribute("cart");

        model.addAttribute("order", order);
        return "checkout-success";
    }
}

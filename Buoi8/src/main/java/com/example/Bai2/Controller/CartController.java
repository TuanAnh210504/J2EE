package com.example.Bai2.Controller;

import com.example.Bai2.Model.CartItem;
import com.example.Bai2.Model.Product;
import com.example.Bai2.Service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Câu 5, 6: Giỏ hàng lưu trong session
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;

    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem> getCart(HttpSession session) {
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    /** Câu 5: Thêm vào giỏ hàng */
    @PostMapping("/add")
    public String addToCart(@RequestParam int productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session) {
        Product product = productService.getProductById(productId);
        if (product == null) return "redirect:/products";

        Map<Integer, CartItem> cart = getCart(session);
        if (cart.containsKey(productId)) {
            // Tăng số lượng nếu sản phẩm đã có
            cart.get(productId).setQuantity(cart.get(productId).getQuantity() + quantity);
        } else {
            CartItem item = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getImage(),
                    product.getPrice(),
                    quantity
            );
            cart.put(productId, item);
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    /** Câu 6: Hiển thị giỏ hàng */
    @GetMapping("")
    public String viewCart(HttpSession session, Model model) {
        Map<Integer, CartItem> cart = getCart(session);
        double total = cart.values().stream().mapToDouble(CartItem::getSubtotal).sum();
        model.addAttribute("cartItems", cart.values());
        model.addAttribute("total", total);
        return "cart";
    }

    /** Xóa 1 sản phẩm khỏi giỏ */
    @GetMapping("/remove/{productId}")
    public String removeItem(@PathVariable int productId, HttpSession session) {
        Map<Integer, CartItem> cart = getCart(session);
        cart.remove(productId);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    /** Cập nhật số lượng */
    @PostMapping("/update")
    public String updateQuantity(@RequestParam int productId,
                                  @RequestParam int quantity,
                                  HttpSession session) {
        Map<Integer, CartItem> cart = getCart(session);
        if (cart.containsKey(productId)) {
            if (quantity <= 0) {
                cart.remove(productId);
            } else {
                cart.get(productId).setQuantity(quantity);
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
}

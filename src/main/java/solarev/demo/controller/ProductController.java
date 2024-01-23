package solarev.demo.controller;

import solarev.demo.dto.ProductDTO;
import solarev.demo.entity.Product;
import solarev.demo.entity.User;
import solarev.demo.entity.Vendor;
import solarev.demo.repository.ProductRepository;
import solarev.demo.repository.UserRepository;
import solarev.demo.repository.VendorRepository;
import solarev.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping
    public String products(Model model) {
        model.addAttribute("productsList", productRepository.findAll());
        model.addAttribute("vendorsList", vendorRepository.findAll());
        return "products";
    }

    @GetMapping("{productId}")
    public String product(@PathVariable Long productId, Model model) {
        model.addAttribute("product", productRepository.findById(productId).get());
        return "productInfo";
    }

    @PostMapping("add")
    @PreAuthorize("hasAuthority('USER')")
    public String addProduct(ProductDTO request) {
        Product product = request.toEntity();
        product.setVendor(vendorRepository.findById(request.getVendorId()).get());
        productRepository.save(product);
        return "redirect:/products/";
    }

    @PostMapping("addToFavourites")
    @PreAuthorize("hasAuthority('USER')")
    public String addToFavourite(Long productId, Authentication authentication) {
        log.info("111111111111111");
        var productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            var user = (User) authentication.getPrincipal();
            log.info("user id = {}", user.getId());
            user = (User) userService.loadUserByUsername(user.getUsername());
            if (!user.getFavoriteProducts().contains(productOptional.get())) {
                user.getFavoriteProducts().add(productOptional.get());
                userService.save(user);
            } else {
                user.getFavoriteProducts().remove(productOptional.get());
                userService.save(user);
            }
        }
        return "redirect:/products/";
    }

    @PostMapping("remove")
    @PreAuthorize("hasAuthority('USER')")
    public String removeProduct(Long id, Authentication authentication) {
        var productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            var user = (User) authentication.getPrincipal();
            log.info("user id = {}", user.getId());
            user = (User) userService.loadUserByUsername(user.getUsername());
            if (!user.getFavoriteProducts().contains(productOptional.get())) {
                user.getFavoriteProducts().remove(productOptional.get());
                userService.save(user);
            }
        }
        productRepository.deleteById(id);
        return "redirect:/products/";
    }
}

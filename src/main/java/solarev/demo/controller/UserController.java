package solarev.demo.controller;

import solarev.demo.entity.User;
import solarev.demo.repository.ProductRepository;
import solarev.demo.repository.UserRepository;
import solarev.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/favourites/")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @GetMapping
    public String favourites(Authentication authentication, Model model) {
        var user = (User) authentication.getPrincipal();
        user = (User) userService.loadUserByUsername(user.getUsername());
        model.addAttribute("user", user);
        return "favourites";
    }
}

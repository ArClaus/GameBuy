package solarev.demo.controller;

import solarev.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Authentication authentication) {
        var user = (User) authentication.getPrincipal();
        log.info("user id = {}", user.getId());
        return "index";
    }
}

package solarev.demo.controller;

import solarev.demo.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vendors/")
@RequiredArgsConstructor
public class VendorController {

    private final VendorRepository vendorRepository;

    @GetMapping
    public String vendors(Model model) {
        model.addAttribute("vendorsList", vendorRepository.findAll());
        return "vendors";
    }
}

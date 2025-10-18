package com.poly.demo.controller;

import com.poly.demo.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Bai4MoreController {

    private List<Product> productList = new ArrayList<>();

    public Bai4MoreController() {
        productList.add(new Product("A", 1.0));
        productList.add(new Product("B", 12.0));
    }

    @GetMapping("/product/formMore")
    public String form(Model model) {
        Product p1 = new Product();
        p1.setName("iPhone 30");
        p1.setPrice(5000.0);

        model.addAttribute("p1", p1);
        model.addAttribute("p2", new Product());
        model.addAttribute("items", productList);

        return "product/formMore";
    }

    @PostMapping("/product/more/save")
    public String save(@ModelAttribute("p2") Product p, Model model) {
        productList.add(p);

        Product p1 = new Product("iPhone 30", 5000.0);
        model.addAttribute("p1", p1);
        model.addAttribute("p2", new Product());
        model.addAttribute("items", productList);

        return "product/formMore";
    }
}

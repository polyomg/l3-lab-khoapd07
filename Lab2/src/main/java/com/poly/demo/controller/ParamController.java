package com.poly.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParamController {

    @RequestMapping("/param/form")
    public String form() {
        return "demo/form";
    }

    @RequestMapping("/param/save/{x}")
    public String save(@PathVariable("x") String x, @RequestParam("y") String y, Model model) {

        // đưa x, y sang view
        model.addAttribute("x", x);
        model.addAttribute("y", y);
        return "demo/form";
    }
}


package com.poly.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ctrl")
public class OkController {

    // POST /ctrl/ok  → OK 1
    @PostMapping("/ok")
    public String m1(Model model) {
        model.addAttribute("methodName", "m1");
        return "demo/ok"; // trả về view ok.html
    }

    // GET /ctrl/ok  → OK 2
    @GetMapping("/ok")
    public String m2(Model model) {
        model.addAttribute("methodName", "m2");
        return "demo/ok";
    }

    // POST /ctrl/ok?x → OK 3
    @PostMapping(value="/ok", params="x")
    public String m3(Model model) {
        model.addAttribute("methodName", "m3");
        return "demo/ok";
    }
}



package com.poly.demo.controller;

import com.poly.demo.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @GetMapping("/product/form")
    public String form(Model model) {
        // tạo sẵn 1 Product
        Product p = new Product();
        p.setName("iPhone 30");
        p.setPrice(5000.0);

        /*?1: đưa Product p vào model để hiển thị trên view */
        model.addAttribute("p1", p);

        //cho rỗng trước chống lỗi
        model.addAttribute("p2", new Product());
        List<Product> items = getItems();
        // đồng thời đưa danh sách sản phẩm xuống view
        model.addAttribute("items", items);

        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) items.size() / pageSize);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", 0);
        model.addAttribute("totalPages", totalPages);

        return "product/form";
    }

    @PostMapping("/product/save")
    public String save(@ModelAttribute("p2") Product p, Model model) {

        // ở đây p chính là dữ liệu nhập từ form
        model.addAttribute("p2", p);

        model.addAttribute("p1", new Product("iPhone 30", 5000.0));

        List<Product> items = getItems();
        model.addAttribute("items", items);

        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) items.size() / pageSize);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNumber", 0);
        model.addAttribute("totalPages", totalPages);

        return "product/form";
    }

    @RequestMapping({"/page", "/page/{size}", "/page/{size}/{number}"})
    public String page(
            @PathVariable(value = "size", required = false) Optional<Integer> optSize,
            @PathVariable(value = "number", required = false) Optional<Integer> optPage,
            Model model) {

        //gắn tạm
        model.addAttribute("p1", new Product("iPhone 30", 5000.0));
        model.addAttribute("p2", new Product());

        int pageSize = optSize.orElse(5);      // mặc định 5 sp/trang
        int pageNumber1Based = optPage.orElse(1); // mặc định trang 1 cho người dùng

        // Chuyển sang 0-based để tính toán
        int pageIndex = (pageNumber1Based < 1) ? 0 : pageNumber1Based - 1;

        List<Product> items = getItems();

        int fromIndex = pageIndex * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, items.size());
        List<Product> pageItems = items.subList(fromIndex, toIndex);

        model.addAttribute("items", pageItems);
        model.addAttribute("pageNumber", pageNumber1Based); // giữ 1-based để hiển thị
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", (int) Math.ceil((double) items.size() / pageSize));

        return "product/form";
    }



    //danh sách sản phẩm  //slide trang 16 (20sp) phan trang 5 sp 1 trang
    /*?3: danh sách sản phẩm */
    public List<Product> getItems() {
        List<Product> list = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            list.add(new Product("Product " + i, i * 10.0));
        }
        return list;
    }

}



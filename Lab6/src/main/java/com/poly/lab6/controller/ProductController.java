package com.poly.lab6.controller;

import com.poly.lab6.dao.ProductDAO;
import com.poly.lab6.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    ProductDAO dao;

    // Hiển thị danh sách và sắp xếp giảm dần theo cột được chọn
    @RequestMapping("/product/sort")
    public String sort(Model model,
                       @RequestParam("field") Optional<String> field) {
        // Nếu chưa chọn cột thì mặc định sắp theo price
        String sortField = field.orElse("price");
        Sort sort = Sort.by(Direction.DESC, sortField);

        // Truy vấn danh sách có sắp xếp
        List<Product> items = dao.findAll(sort);

        // Gửi dữ liệu qua view
        model.addAttribute("items", items);
        model.addAttribute("field", sortField.toUpperCase());
        return "product/sort";
    }

    @RequestMapping("/product/page")
    public String paginate(Model model,
                           @RequestParam("p") Optional<Integer> p) {
        // Nếu không có tham số p thì mặc định là 0 (trang đầu tiên)
        Pageable pageable = PageRequest.of(p.orElse(0), 5);
        Page<Product> page = dao.findAll(pageable);

        model.addAttribute("page", page);
        return "product/page";
    }
}
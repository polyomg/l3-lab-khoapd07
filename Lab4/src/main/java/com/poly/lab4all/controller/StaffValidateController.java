package com.poly.lab4all.controller;

import com.poly.lab4all.entity.Staff;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/staff")
public class StaffValidateController {

    @RequestMapping("/create/form-validate")
    public String createFormValidate(Model model, @ModelAttribute("staff") Staff staff) {
        model.addAttribute("message", "Vui lòng nhập thông tin nhân viên!");
        return "staff-validate"; // view riêng cho bài 2
    }

    @RequestMapping("/create/save-validate")
    public String createSaveValidate(Model model,
                                     @RequestPart("photo_file") MultipartFile photoFile,
                                     @Valid @ModelAttribute("staff") Staff staff,
                                     Errors errors) {

        if (!photoFile.isEmpty()) {
            staff.setPhoto(photoFile.getOriginalFilename());
        }

        if (errors.hasErrors()) {
            model.addAttribute("message", "Vui lòng sửa các lỗi sau!");
        } else {
            model.addAttribute("message", "Dữ liệu đã nhập đúng!");
        }

        return "staff-validate"; // view riêng cho bài 2
    }
}



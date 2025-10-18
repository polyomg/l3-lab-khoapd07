package com.poly.lab4.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Staff {
    private String id;             // email
    private String fullname;       // họ và tên

    @Builder.Default
    private String photo = "photo.jpg";

    @Builder.Default
    private Boolean gender = true; // true = Nam, false = Nữ

    @Builder.Default
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private Date birthday = new Date();

    @Builder.Default
    private double salary = 12345.6789;

    @Builder.Default
    private Integer level = 0;     // 0=Úy, 1=Tá, 2=Tướng
}


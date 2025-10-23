package com.example.demo.service;

import lombok.Builder;
import lombok.Data;
// import lombok.experimental.Default; // <--- XÓA DÒNG NÀY ĐI

public interface MailService {

    @Data
    @Builder
    public static class Mail {
        @Builder.Default // <--- THAY THẾ BẰNG @Builder.Default
        private String from = "WebShop <web-shop@gmail.com>";
        private String to, cc, bcc, subject, body, filenames;
    }

    void send(Mail mail);

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        this.send(mail);
    }

    void push(Mail mail);

    default void push(String to, String subject, String body) {
        Mail mail = Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        this.push(mail);
    }
}
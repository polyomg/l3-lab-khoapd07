package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled; // <-- THÊM DÒNG NÀY

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList; // <-- THAY THẾ ArrayList bằng CopyOnWriteArrayList để an toàn với nhiều luồng

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    // Hàng đợi email cho Bài 2
    // THAY THẾ ArrayList bằng CopyOnWriteArrayList để tránh lỗi khi nhiều luồng truy cập
    List<Mail> queue = new CopyOnWriteArrayList<>();

    @Override
    public void send(Mail mail) {
        try {
            // 1. Tạo Mail
            MimeMessage message = mailSender.createMimeMessage();

            // 2. Tạo đối tượng hỗ trợ ghi nội dung Mail
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            // 2.1. Ghi thông tin người gửi
            helper.setFrom(mail.getFrom());
            helper.setReplyTo(mail.getFrom()); // Đặt reply-to giống from

            // 2.2. Ghi thông tin người nhận
            helper.setTo(mail.getTo());
            if (!isNullOrEmpty(mail.getCc())) {
                helper.setCc(mail.getCc());
            }
            if (!isNullOrEmpty(mail.getBcc())) {
                helper.setBcc(mail.getBcc());
            }

            // 2.3. Ghi tiêu đề và nội dung
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getBody(), true); // true cho phép HTML

            // 2.4. Đính kèm file
            String filenames = mail.getFilenames();
            if (!isNullOrEmpty(filenames)) {
                for (String filename : filenames.split("[,;]+")) {
                    File file = new File(filename.trim());
                    if (file.exists()) { // Kiểm tra file có tồn tại không
                        helper.addAttachment(file.getName(), file);
                    } else {
                        System.err.println("File not found: " + filename);
                    }
                }
            }

            // 3. Gửi Mail
            mailSender.send(message);
            System.out.println("--- Mail đã được gửi thành công: " + mail.getSubject() + " tới " + mail.getTo());
        } catch (MessagingException e) {
            System.err.println("!!! Gửi mail thất bại: " + e.getMessage() + " cho " + mail.getSubject() + " tới " + mail.getTo());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private boolean isNullOrEmpty(String text) {
        return (text == null || text.trim().length() == 0);
    }

    @Override
    public void push(Mail mail) {
        queue.add(mail);
        System.out.println(">>> Mail đã được thêm vào hàng đợi: " + mail.getSubject() + " cho " + mail.getTo() + ". Kích thước hàng đợi hiện tại: " + queue.size());
    }

    @Override
    public void push(String to, String subject, String body) {
        Mail mail = Mail.builder()
                .to(to)
                .subject(subject)
                .body(body)
                .build();
        this.push(mail);
    }

    /**
     * Phương thức này sẽ chạy định kỳ để xử lý các email trong hàng đợi.
     * fixedDelay = 5000: nghĩa là sau khi tác vụ kết thúc, nó sẽ đợi 5000ms (5 giây) rồi mới chạy lại.
     */
    @Scheduled(fixedDelay = 5000)
    public void processMailQueue() {
        if (!queue.isEmpty()) {
            System.out.println("=== Đang xử lý hàng đợi mail. Số lượng mail: " + queue.size());
            List<Mail> mailsToSend = new ArrayList<>(queue); // Tạo bản sao để tránh ConcurrentModificationException
            queue.clear(); // Xóa tất cả các mail hiện có trong queue

            for (Mail mail : mailsToSend) {
                try {
                    send(mail); // Gọi phương thức send đã có của bạn để gửi mail
                } catch (RuntimeException e) {
                    System.err.println("!!! Lỗi khi gửi mail từ hàng đợi: " + e.getMessage() + " cho " + mail.getSubject() + " tới " + mail.getTo());
                    // Tùy chọn: Xử lý lỗi, ví dụ: đẩy lại vào queue sau một thời gian, hoặc ghi vào một log lỗi riêng
                }
            }
            System.out.println("=== Hoàn tất xử lý hàng đợi mail.");
        }
    }
}
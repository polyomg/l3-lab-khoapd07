package com.example.demo.controller;

import com.example.demo.service.MailService;
import com.example.demo.service.MailService.Mail; // Import the Mail DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Import Model
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import PostMapping
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // Import RequestParam
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    MailService mailService;

    // Existing /send endpoint for direct sending (as per your code)
    @GetMapping("/send")
    @ResponseBody
    public String sendDirect() {
        try {
            mailService.send("dung9012@gmail.com", "Hello from Spring Boot", "This is a test email saying hello!");
            return "Mail của bạn đã được gửi đi";
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "Gửi mail thất bại: " + e.getMessage();
        }
    }

    // Existing /push endpoint for queuing (as per your code)
    @GetMapping("/push")
    @ResponseBody
    public String pushMail() {
        mailService.push("dung9012@gmail.com", "Subject from Queue", "Body of the queued email.");
        return "Mail của bạn đã được xếp vào hàng đợi";
    }

    // New endpoint for the email composition form
    @GetMapping("/compose")
    public String composeMailForm(Model model) {
        // You can set a default 'from' address if needed
        model.addAttribute("defaultFrom", "WebShop <web-shop@gmail.com>");
        return "send_mail_form"; // Refers to send_mail_form.html
    }

    @PostMapping("/compose")
    public String handleComposeMail(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam(value = "cc", required = false) String cc,
            @RequestParam(value = "bcc", required = false) String bcc,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            @RequestParam(value = "filenames", required = false) String filenames,
            @RequestParam("action") String action, // To distinguish between "send" and "push"
            Model model) {

        Mail mail = Mail.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .filenames(filenames)
                .build();

        try {
            if ("send".equals(action)) {
                mailService.send(mail);
                model.addAttribute("message", "Mail của bạn đã được gửi đi trực tiếp!");
            } else if ("push".equals(action)) {
                mailService.push(mail);
                model.addAttribute("message", "Mail của bạn đã được xếp vào hàng đợi!");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error", "Gửi/Xếp mail thất bại: " + e.getMessage());
        }
        model.addAttribute("defaultFrom", from); // Keep the 'from' value
        return "send_mail_form"; // Return to the form with a message
    }
}
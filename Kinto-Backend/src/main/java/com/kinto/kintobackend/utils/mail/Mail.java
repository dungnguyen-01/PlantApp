package com.kinto.kintobackend.utils.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    @Value("${spring.mail.username}")
    private String email;
    String to, subject, text, from, cc, bcc, attachments;
    /**
     * Tạo Mail object từ các tham số:
     * @param to là email người nhận
     * @param subject là tiêu đề mail
     * @param text là nội dụng mail
     */
    public Mail(String to, String subject, String text) {
        this(to, subject, text, Map.of());
    }
    /**
     * Tạo Mail object từ các tham số:
     * @param to là email người nhận
     * @param subject là tiêu đề mail
     * @param text là nội dụng mail
     * @param opts là Map&lt;String, String&gt; chứa các thông tin khác như from, cc, bcc, attatchments
     */
    public Mail(String to, String subject, String text, Map<String, String> opts) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.from = opts.getOrDefault("from", "App Kinto <"+email+">");
        this.cc = opts.get("cc");
        this.bcc = opts.get("bcc");
        this.attachments = opts.get("attachments");
    }
}
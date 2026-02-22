//package com.example.demo.service;
//
//
//
//import com.example.demo.entity.Otp;
//import com.example.demo.entity.User;
//import com.example.demo.repository.OtpRepository;
//import com.example.demo.repository.UserRepository;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.internet.MimeMessage;
//import java.security.SecureRandom;
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//@Service
//public class OtpService {
//
//    private final OtpRepository otpRepository;
//    private final UserRepository userRepository;
//    private final JavaMailSender mailSender;
//
//    private static final int OTP_EXPIRY_MINUTES = 5;
//
//    public OtpService(OtpRepository otpRepository,
//                      UserRepository userRepository,
//                      JavaMailSender mailSender) {
//        this.otpRepository = otpRepository;
//        this.userRepository = userRepository;
//        this.mailSender = mailSender;
//    }
//
//    // ── Generate & Send OTP ────────────────────────────────────────────
//    public void sendOtp(String email, String purpose) {
//        // Validate purpose
//        if (!purpose.equals("LOGIN") && !purpose.equals("REGISTER")) {
//            throw new RuntimeException("Invalid OTP purpose");
//        }
//
//        // For LOGIN: user must exist
//        if (purpose.equals("LOGIN")) {
//            userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("No account found with this email"));
//        }
//
//        // For REGISTER: email must NOT exist
//        if (purpose.equals("REGISTER")) {
//            if (userRepository.findByEmail(email).isPresent()) {
//                throw new RuntimeException("Email already registered. Please login.");
//            }
//        }
//
//        // Generate 6-digit OTP
//        String otpCode = generateOtp();
//        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);
//
//        // Save to DB
//        Otp otp = new Otp(email, otpCode, expiresAt, purpose);
//        otpRepository.save(otp);
//
//        // Send email
//        sendOtpEmail(email, otpCode, purpose);
//    }
//
//    // ── Verify OTP ─────────────────────────────────────────────────────
//    public boolean verifyOtp(String email, String otpCode, String purpose) {
//        Optional<Otp> otpOpt = otpRepository.findLatestValid(
//            email, purpose, LocalDateTime.now()
//        );
//
//        if (otpOpt.isEmpty()) {
//            throw new RuntimeException("OTP expired or not found. Please request a new one.");
//        }
//
//        Otp otp = otpOpt.get();
//
//        if (!otp.getOtpCode().equals(otpCode)) {
//            throw new RuntimeException("Invalid OTP. Please check and try again.");
//        }
//
//        // Mark as used
//        otp.setUsed(true);
//        otpRepository.save(otp);
//
//        return true;
//    }
//
//    // ── Generate random 6-digit OTP ────────────────────────────────────
//    private String generateOtp() {
//        SecureRandom random = new SecureRandom();
//        int otp = 100000 + random.nextInt(900000);
//        return String.valueOf(otp);
//    }
//
//    // ── Send Email ─────────────────────────────────────────────────────
//    private void sendOtpEmail(String email, String otpCode, String purpose) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//            helper.setTo(email);
//            helper.setSubject(purpose.equals("LOGIN")
//                ? "🔐 Your Login OTP - Finance AI"
//                : "✅ Verify Your Email - Finance AI");
//
//            helper.setText(buildEmailHtml(otpCode, purpose), true);
//
//            mailSender.send(message);
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
//        }
//    }
//
//    // ── HTML Email Template ────────────────────────────────────────────
//    private String buildEmailHtml(String otpCode, String purpose) {
//        String title = purpose.equals("LOGIN") ? "Login Verification" : "Email Verification";
//        String subtitle = purpose.equals("LOGIN")
//            ? "Use this OTP to login to your account"
//            : "Use this OTP to verify your email and complete registration";
//
//        return """
//            <!DOCTYPE html>
//            <html>
//            <head>
//                <meta charset="UTF-8">
//                <style>
//                    body { font-family: Arial, sans-serif; background: #0a0e1a; margin: 0; padding: 20px; }
//                    .container { max-width: 500px; margin: 0 auto; background: #1e293b; border-radius: 16px; padding: 40px; }
//                    .logo { text-align: center; font-size: 28px; font-weight: bold; color: #8b5cf6; margin-bottom: 8px; }
//                    .title { text-align: center; color: #f1f5f9; font-size: 22px; font-weight: bold; margin: 20px 0 8px; }
//                    .subtitle { text-align: center; color: #94a3b8; font-size: 14px; margin-bottom: 32px; }
//                    .otp-box { background: #0f172a; border: 2px solid #8b5cf6; border-radius: 12px; padding: 24px; text-align: center; margin: 24px 0; }
//                    .otp-label { color: #94a3b8; font-size: 13px; margin-bottom: 12px; }
//                    .otp-code { font-size: 42px; font-weight: bold; letter-spacing: 12px; color: #8b5cf6; font-family: monospace; }
//                    .expiry { text-align: center; color: #64748b; font-size: 13px; margin-top: 20px; }
//                    .warning { background: #1a1a2e; border-left: 4px solid #ef4444; padding: 12px 16px; border-radius: 8px; margin-top: 24px; }
//                    .warning p { color: #94a3b8; font-size: 12px; margin: 4px 0; }
//                    .footer { text-align: center; color: #475569; font-size: 12px; margin-top: 32px; }
//                </style>
//            </head>
//            <body>
//                <div class="container">
//                    <div class="logo">₹ Finance AI</div>
//                    <div class="title">%s</div>
//                    <div class="subtitle">%s</div>
//
//                    <div class="otp-box">
//                        <div class="otp-label">Your One-Time Password</div>
//                        <div class="otp-code">%s</div>
//                    </div>
//
//                    <div class="expiry">⏱️ This OTP expires in <strong style="color:#f1f5f9">5 minutes</strong></div>
//
//                    <div class="warning">
//                        <p>⚠️ Never share this OTP with anyone</p>
//                        <p>🔒 Finance AI will never ask for your OTP</p>
//                        <p>❌ If you didn't request this, ignore this email</p>
//                    </div>
//
//                    <div class="footer">
//                        © 2026 Finance AI. All rights reserved.
//                    </div>
//                </div>
//            </body>
//            </html>
//            """.formatted(title, subtitle, otpCode);
//    }
//}


package com.example.demo.service;

import com.example.demo.entity.Otp;
import com.example.demo.repository.OtpRepository;
import com.example.demo.repository.UserRepository;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Value("${SENDGRID_API_KEY}")
    private String sendGridApiKey;

    @Value("${MAIL_FROM}")
    private String fromEmail;

    private static final int OTP_EXPIRY_MINUTES = 5;

    public OtpService(OtpRepository otpRepository,
                      UserRepository userRepository) {
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }

    // ── Generate & Send OTP ────────────────────────────────────────────
    public String sendOtp(String email, String purpose) {
        if (!purpose.equals("LOGIN") && !purpose.equals("REGISTER") && !purpose.equals("TEST")) {
            throw new RuntimeException("Invalid OTP purpose");
        }

        if (purpose.equals("LOGIN")) {
            userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No account found with this email"));
        }

        if (purpose.equals("REGISTER")) {
            if (userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Email already registered. Please login.");
            }
        }

        String otpCode = generateOtp();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES);

        Otp otp = new Otp(email, otpCode, expiresAt, purpose);
        otpRepository.save(otp);

        sendOtpEmail(email, otpCode, purpose);

        return "OTP sent successfully to " + email;
    }

    // ── Verify OTP ─────────────────────────────────────────────────────
    public String verifyOtp(String email, String otpCode, String purpose) {
        Optional<Otp> otpOpt = otpRepository.findLatestValid(
            email, purpose, LocalDateTime.now()
        );

        if (otpOpt.isEmpty()) {
            throw new RuntimeException("OTP expired or not found. Please request a new one.");
        }

        Otp otp = otpOpt.get();

        if (!otp.getOtpCode().equals(otpCode)) {
            throw new RuntimeException("Invalid OTP. Please check and try again.");
        }

        otp.setUsed(true);
        otpRepository.save(otp);

        return "OTP verified successfully";
    }

    // ── Generate random 6-digit OTP ────────────────────────────────────
    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // ── Send Email via SendGrid HTTP API ───────────────────────────────
    private void sendOtpEmail(String email, String otpCode, String purpose) {
        try {
            Email from = new Email(fromEmail);
            Email to = new Email(email);
            String subject = purpose.equals("LOGIN")
                ? "🔐 Your Login OTP - Finance AI"
                : "✅ Verify Your Email - Finance AI";

            Content content = new Content("text/html", buildEmailHtml(otpCode, purpose));
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid error: " + response.getBody());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to send OTP email: " + e.getMessage());
        }
    }

    // ── HTML Email Template ────────────────────────────────────────────
    private String buildEmailHtml(String otpCode, String purpose) {
        String title = purpose.equals("LOGIN") ? "Login Verification" : "Email Verification";
        String subtitle = purpose.equals("LOGIN")
            ? "Use this OTP to login to your account"
            : "Use this OTP to verify your email and complete registration";

        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; background: #0a0e1a; margin: 0; padding: 20px; }
                    .container { max-width: 500px; margin: 0 auto; background: #1e293b; border-radius: 16px; padding: 40px; }
                    .logo { text-align: center; font-size: 28px; font-weight: bold; color: #8b5cf6; margin-bottom: 8px; }
                    .title { text-align: center; color: #f1f5f9; font-size: 22px; font-weight: bold; margin: 20px 0 8px; }
                    .subtitle { text-align: center; color: #94a3b8; font-size: 14px; margin-bottom: 32px; }
                    .otp-box { background: #0f172a; border: 2px solid #8b5cf6; border-radius: 12px; padding: 24px; text-align: center; margin: 24px 0; }
                    .otp-label { color: #94a3b8; font-size: 13px; margin-bottom: 12px; }
                    .otp-code { font-size: 42px; font-weight: bold; letter-spacing: 12px; color: #8b5cf6; font-family: monospace; }
                    .expiry { text-align: center; color: #64748b; font-size: 13px; margin-top: 20px; }
                    .warning { background: #1a1a2e; border-left: 4px solid #ef4444; padding: 12px 16px; border-radius: 8px; margin-top: 24px; }
                    .warning p { color: #94a3b8; font-size: 12px; margin: 4px 0; }
                    .footer { text-align: center; color: #475569; font-size: 12px; margin-top: 32px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="logo">₹ Finance AI</div>
                    <div class="title">%s</div>
                    <div class="subtitle">%s</div>

                    <div class="otp-box">
                        <div class="otp-label">Your One-Time Password</div>
                        <div class="otp-code">%s</div>
                    </div>

                    <div class="expiry">⏱️ This OTP expires in <strong style="color:#f1f5f9">5 minutes</strong></div>

                    <div class="warning">
                        <p>⚠️ Never share this OTP with anyone</p>
                        <p>🔒 Finance AI will never ask for your OTP</p>
                        <p>❌ If you didn't request this, ignore this email</p>
                    </div>

                    <div class="footer">
                        © 2026 Finance AI. All rights reserved.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(title, subtitle, otpCode);
    }
}

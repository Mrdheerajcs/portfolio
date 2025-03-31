package com.auspicius.Services.Impl;

import com.auspicius.Entity.ContactMessage;
import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.User;
import com.auspicius.Repository.ContactRepository;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.ContactService;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ContactMsgReq;
import com.auspicius.utils.ResponseUtils;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ApiResponse<ContactMessage> createContactMessage(ContactMsgReq contactMsgReq) {
        try {
            // Validate user
            User user = userRepository.findById(contactMsgReq.getUser())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid or missing user ID."));

            if (!userRepository.isUserActiveById(user.getId())) {
                throw new IllegalArgumentException("User is deactivated.");
            }

            // Validate portfolio
            Portfolio portfolio = portfolioRepository.findById(contactMsgReq.getPortfolio())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid or missing portfolio ID."));

            if (!portfolioRepository.isPortfolioActiveById(portfolio.getId())) {
                throw new IllegalArgumentException("Portfolio is deactivated.");
            }

            // Validate required fields
            if (contactMsgReq.getName() == null || contactMsgReq.getName().isBlank()) {
                throw new IllegalArgumentException("Name is required.");
            }
            if (contactMsgReq.getEmail() == null || contactMsgReq.getEmail().isBlank()) {
                throw new IllegalArgumentException("Email is required.");
            }
            if (contactMsgReq.getMessage() == null || contactMsgReq.getMessage().isBlank()) {
                throw new IllegalArgumentException("Message is required.");
            }

            // Map request to entity and save
            ContactMessage message = new ContactMessage();
            message.setUser(user);
            message.setPortfolio(portfolio);
            message.setName(contactMsgReq.getName());
            message.setEmail(contactMsgReq.getEmail());
            message.setMessage(contactMsgReq.getMessage());
            message.setCreatedOn(Helper.getCurrentTimeStamp());
            message.setUpdatedOn(Helper.getCurrentTimeStamp());

            ContactMessage savedContactMessage = contactRepository.save(message);

            // Send email after saving
            sendEmail(user.getEmail(), contactMsgReq.getName(), contactMsgReq.getEmail(), contactMsgReq.getMessage());

            return ResponseUtils.createSuccessResponse(savedContactMessage);

        } catch (IllegalArgumentException e) {
            return ResponseUtils.createFailureResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        } catch (Exception e) {
            return ResponseUtils.createFailureResponse("An error occurred while saving the contact message.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private void sendEmail(String recipientEmail, String senderName, String senderEmail, String messageText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("📩 New Contact Inquiry from " + senderName);

            String emailContent = "<div style='font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;'>"
                    + "<div style='max-width: 600px; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1);'>"
                    + "<h2 style='color: #333; text-align: center;'>📬 New Contact Message Received</h2>"
                    + "<hr style='border: 1px solid #ddd;'>"
                    + "<p style='font-size: 16px;'><strong>👤 Sender Name:</strong> " + senderName + "</p>"
                    + "<p style='font-size: 16px;'><strong>📧 Sender Email:</strong> <a href='mailto:" + senderEmail + "' style='color: #007BFF; text-decoration: none;'>" + senderEmail + "</a></p>"
                    + "<p style='font-size: 16px;'><strong>💬 Message:</strong></p>"
                    + "<blockquote style='font-size: 14px; color: #555; background: #f9f9f9; padding: 15px; border-left: 5px solid #007BFF;'>"
                    + messageText + "</blockquote>"
                    + "<br>"
                    + "<div style='text-align: center;'>"
                    + "<a href='mailto:" + senderEmail + "' style='display: inline-block; background-color: #007BFF; color: #fff; padding: 10px 20px; border-radius: 5px; text-decoration: none; font-size: 16px;'>Reply Now</a>"
                    + "</div>"
                    + "<br>"
                    + "<p style='text-align: center; font-size: 14px; color: #777;'>💡 Need more details? Contact our support team anytime.</p>"
                    + "<p style='text-align: center; font-size: 14px; color: #777;'>🔗 <a href='https://yourwebsite.com' style='color: #007BFF;'>Visit our website</a></p>"
                    + "</div>"
                    + "</div>";

            helper.setText(emailContent, true); // Set HTML content

            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage()); // Log error without breaking main flow
        }
    }

}

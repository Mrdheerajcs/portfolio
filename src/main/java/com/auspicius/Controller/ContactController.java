package com.auspicius.Controller;


import com.auspicius.Entity.ContactMessage;
import com.auspicius.Services.ContactService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ContactMsgReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/send")
    public ApiResponse<ContactMessage> createContactMessage(@RequestBody ContactMsgReq contactMsgReq) {
        return contactService.createContactMessage(contactMsgReq);
    }
}

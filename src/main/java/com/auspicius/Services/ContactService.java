package com.auspicius.Services;

import com.auspicius.Entity.ContactMessage;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.ContactMsgReq;

public interface ContactService {
    ApiResponse<ContactMessage> createContactMessage(ContactMsgReq contactMsgReq);
}

package com.auspicius.Services;

import com.auspicius.Entity.SocialLink;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SocialLinkRequest;
import com.auspicius.responce.UserSocialRes;

import java.util.List;

public interface SocialLinkService {


   ApiResponse<SocialLink> addSocialLink(SocialLinkRequest request);

    ApiResponse<List<UserSocialRes>> getUserSocialLinks(Integer userId);

    ApiResponse<SocialLink> updateSocialLink(Integer linkId, SocialLink updatedLink);

    ApiResponse<String> deleteSocialLink(Integer linkId);
}

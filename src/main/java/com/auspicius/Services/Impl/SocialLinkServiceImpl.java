package com.auspicius.Services.Impl;

import com.auspicius.Entity.Portfolio;
import com.auspicius.Entity.SocialLink;
import com.auspicius.Entity.User;
import com.auspicius.Repository.PortfolioRepository;
import com.auspicius.Repository.SocialLinkRepository;
import com.auspicius.Repository.UserRepository;
import com.auspicius.Services.SocialLinkService;
import com.auspicius.exception.SDDException;
import com.auspicius.helperUtil.Helper;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SocialLinkRequest;
import com.auspicius.responce.UserSocialRes;
import com.auspicius.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SocialLinkServiceImpl implements SocialLinkService {

    @Autowired
    private SocialLinkRepository socialLinkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public ApiResponse<SocialLink> addSocialLink(Integer userId, Integer portfolioId, SocialLinkRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new SDDException("user", HttpStatus.NOT_FOUND.value(), "User not found"));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new SDDException("portfolio", HttpStatus.NOT_FOUND.value(), "Portfolio not found"));

        SocialLink socialLink = new SocialLink();
        socialLink.setUser(user);
        socialLink.setPortfolio(portfolio);
        socialLink.setName(request.getName());
        socialLink.setUrl(request.getUrl());
        socialLink.setCreatedOn(Helper.getCurrentTimeStamp());
        socialLink.setUpdatedOn(Helper.getCurrentTimeStamp());

        SocialLink savedLink = socialLinkRepository.save(socialLink);
        return ResponseUtils.createSuccessResponse(savedLink);
    }

    @Override
    public ApiResponse<List<UserSocialRes>> getUserSocialLinks(Integer userId) {
        List<SocialLink> socialLinks = socialLinkRepository.findByUserId(userId);

        List<UserSocialRes> responseList = socialLinks.stream().map(socialLink -> {
            UserSocialRes res = new UserSocialRes();
            res.setId(socialLink.getId());
            res.setName(socialLink.getName());
            res.setUrl(socialLink.getUrl());
            res.setUserId(socialLink.getUser().getId());
            return res;
        }).toList();

        return ResponseUtils.createSuccessResponse(responseList);
    }

    @Override
    public ApiResponse<SocialLink> updateSocialLink(Integer linkId, SocialLink updatedLink) {
        SocialLink existingLink = socialLinkRepository.findById(linkId)
                .orElseThrow(() -> new SDDException("socialLink", HttpStatus.NOT_FOUND.value(), "Social link not found"));

        if (updatedLink.getUrl() != null) {
            existingLink.setUrl(updatedLink.getUrl());
        }
        if (updatedLink.getName() != null) {
            existingLink.setName(updatedLink.getName());
        }

        existingLink.setUpdatedOn(Helper.getCurrentTimeStamp());
        SocialLink savedLink = socialLinkRepository.save(existingLink);
        return ResponseUtils.createSuccessResponse(savedLink);
    }


    @Override
    public ApiResponse<String> deleteSocialLink(Integer linkId) {
        if (!socialLinkRepository.existsById(linkId)) {
            throw new SDDException("socialLink", HttpStatus.NOT_FOUND.value(), "Social link not found");
        }

        socialLinkRepository.deleteById(linkId);
        return ResponseUtils.createSuccessResponse("Social link deleted successfully");
    }
}

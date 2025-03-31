package com.auspicius.Controller;

import com.auspicius.Entity.SocialLink;
import com.auspicius.Services.SocialLinkService;
import com.auspicius.responce.ApiResponse;
import com.auspicius.responce.SocialLinkRequest;
import com.auspicius.responce.UserSocialRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/social-links")
@RequiredArgsConstructor
public class SocialLinkController {
    private final SocialLinkService socialLinkService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SocialLink>> addSocialLink(@RequestBody SocialLinkRequest request) {
        return ResponseEntity.ok(socialLinkService.addSocialLink( request));
    }

    @GetMapping("/getByUser/{userId}")
    public ResponseEntity<ApiResponse<List<UserSocialRes>>> getUserSocialLinks(@PathVariable Integer userId) {
        return ResponseEntity.ok(socialLinkService.getUserSocialLinks(userId));
    }

    @PutMapping("/updateById/{linkId}")
    public ResponseEntity<ApiResponse<SocialLink>> updateSocialLink(@PathVariable Integer linkId, @RequestBody SocialLink updatedLink) {
        return ResponseEntity.ok(socialLinkService.updateSocialLink(linkId, updatedLink));
    }


    @DeleteMapping("/deleteById/{linkId}")
    public ResponseEntity<ApiResponse<String>> deleteSocialLink(@PathVariable Integer linkId) {
        return ResponseEntity.ok(socialLinkService.deleteSocialLink(linkId));
    }
}


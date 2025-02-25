package com.test.lsy.security2.controller;

import com.test.lsy.security2.config.security.auth.PrincipalDetails;
import com.test.lsy.security2.model.User;
import com.test.lsy.security2.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final UserService service;

    @GetMapping("/test/login")
    @ResponseBody
    public String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails userDetails) {
        log.info("/test/login==========================");
        log.info("authentication: {}", userDetails.getUser());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    @ResponseBody
    public String testOauthLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails oAuth) {
        log.info("/test/oauth/login==========================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("authentication: {}", oAuth2User.getAttributes());
        log.info("oAuth2User: {}", oAuth.getAttributes());
        return "Oauth 세션 정보 확인";
    }

    @GetMapping("/user")
    public  String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails != null) {
            log.info("principalDetails: {}", principalDetails.getUser());
        }
        return "user";
    }

    @GetMapping("/admin")
    public  String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public  String manager() {
        return "manager";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/joinProc")
    public String joinProc(User user, RedirectAttributes rd) {
        service.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public String info() {
        return "info";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public String data() {
        return "data";
    }
}

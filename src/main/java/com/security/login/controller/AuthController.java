package com.security.login.controller;

import com.security.login.dto.JwtRequestDto;
import com.security.login.dto.MemberSignUpRequestDto;
import com.security.login.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login (@RequestBody JwtRequestDto request) {
        try{
            return authService.login(request);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    // 회원가입
    @PostMapping(value = "signup", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signup(@RequestBody MemberSignUpRequestDto request) {
        return authService.signUp(request);
    }
}

package com.security.login.service;

import com.security.login.dto.JwtRequestDto;
import com.security.login.dto.MemberSignUpRequestDto;
import com.security.login.entity.Member;
import com.security.login.repository.MemberRepository;
import com.security.login.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    // 회원가입 서비스
    public String signUp(MemberSignUpRequestDto request) {
        boolean existMember = memberRepository.existsById(request.getEmail());

        // 이미 회원이 존재하는 경우
        if(existMember)
            return null;

        // 아닌경우 새로 가입
        Member member = new Member(request); // 입력받은 request값으로 new member 생성
        member.encryptPassword(passwordEncoder); // password는 인코더 걸어줌

        memberRepository.save(member);
        return member.getEmail();
    }

    // 로그인 서비스
    public String login(JwtRequestDto request) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return principal.getUsername();
    }
}

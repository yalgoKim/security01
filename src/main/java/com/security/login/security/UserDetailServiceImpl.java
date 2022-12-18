package com.security.login.security;

import com.security.login.entity.Member;
import com.security.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    // UserDetailsService 및 UserDetails 구현을 통해 로그인 처리를 하고 인증된 사용자를 어떤 형태로 반환할지 정하는 객체를 생성

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username)
                .orElseThrow(()-> new UsernameNotFoundException("등록되지 않은 사용자 입니다"));
        return new UserDetailsImpl(member);
    }
}

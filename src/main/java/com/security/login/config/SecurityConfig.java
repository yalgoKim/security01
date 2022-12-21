package com.security.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // configure : WebSecurityConfigureAdapter를 상속받을 때 사용하는 메서드
    // http 관련 인증 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 권한 부여에 따른 접근 설정
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/file/**",
                        "/image/**",
                        "/swagger/**",
                        "/swagger-ui/**",
                        "/h2-console/**",
                        "/favicon.ico",
                        "/h2/**"
                ).permitAll()
                // login 없이 접근 허용 하는 url : 로그인, 회원가입, 유저화면은 누구나 접근 가능함 // permitAll() : 누구나 접근이 가능
                .antMatchers("/auth/**").permitAll()
                // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능 // hasRole() : 특정 권한이 있는 사람만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN")
                // 나머지 요청은 권한의 종류 상관 없이 무조건 권한 있어야 접근 가능 // authenticated() : 권한이 있으면 무조건 접근 가능 // anyRequest() : antMatchers에서 설정하지 않은 나머지 경로
                .anyRequest().authenticated()
                .and()
                .formLogin()// 로그인에 대한 설정
                .loginPage("/login") // 로그인 페이지 링크 설정
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 할 주소
                .and()
                .logout()
                .logoutSuccessUrl("/login") // 로그아웃 후 리다이렉트 할 주소 (로그아웃 후 로그인화면 띄울예정)
                .invalidateHttpSession(true) // 로그아웃 시 세션 전체 삭제 여부 true
                .and()
                .csrf().disable().authorizeRequests() // csrf 에러 발생 방지
                .and()
                .headers().frameOptions().disable();
    }

    // 인증을 무시할 경로를 설정
    @Override
    public void configure(WebSecurity web) {
        // 정적인 파일 : 기본적인 화면구현부분(css, js 등) security 적용 무시 <- static 하위 폴더 라고도 함..
        web.ignoring().antMatchers("/css/**", "/js/**", "img/**");
    }

    @Bean
    public BCryptPasswordEncoder encodePassword(){ // 회원가입 시 비밀번호 암호화에 사용할 Encoder 빈 등록
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 로그인 할 때 필요한 정보를 가져오는 곳
//        auth.userDetailsService(userService) // 유저 정보를 가져오는 서비스 지정
//                .passwordEncoder((new BCryptPasswordEncoder())); // 패스워드 인코더 사용
//    }

}

package com.oasis.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity // 스프링 시큐리티가 스프링 체인 필터에 등록. 기본적인 web 보안 활성화
public class SecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return
                http
                        .authorizeRequests() //HttpServeletRequest를 사용하는 요청들에대한 접근제한을 설정
                        .antMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                        .and().build();
    }

}

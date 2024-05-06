package net.wattmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import net.wattmarket.handler.CustomFailureHandler;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomFailureHandler cfHandler;
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(
            (auth) -> auth
            // .requestMatchers(
            //     "/analysis/report/getProsumerData"
            // ).hasRole("PROSUMER")
            // .requestMatchers(
            //     "/analysis/report/getConsumerData"
            // ).hasRole("CONSUMER")
            .anyRequest().permitAll()
        );

        http
        .formLogin(
            (auth) -> auth
                .loginPage("/loginForm")
                .failureHandler(cfHandler)
                .usernameParameter("memberId")
                .passwordParameter("memberPw")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/").permitAll()
        );

        http
        .logout(
            (auth) -> auth
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONONID")
        );

        http
        .csrf(
            (auth)->auth.disable()
        );
        return http.build();
    }
    @Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

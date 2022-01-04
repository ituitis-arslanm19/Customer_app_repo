package com.example.demo.security;

import com.example.demo.filter.CustomAuthenticationFilter;
import com.example.demo.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor @Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/swagger-ui/**","/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Authtentication için kullandığım custom filtreyi burada oluşturdum
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        //Hangi sayfalara hangi role sahip customerların girebileceğini belirlediğim kısım , ayrıca login yapmadan diğer sayfalara geçilememesini de burada kontrol ediyorum.
        log.debug("HTTP settings are configuring.");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/swagger-ui.html","/swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers("/login/").permitAll().and().formLogin().defaultSuccessUrl("/api",true).failureForwardUrl("/api");
        http.authorizeRequests().antMatchers(GET,"/api").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/api/getCustomer").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(POST,"/api/addCustomer").hasAnyAuthority("ROLE_ADMIN");
        //Swagger dokümantasyonuna erişmek için bir alt satırı komut yapmanız gerekiyor.
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);
        log.debug("Configuration completed.");





    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

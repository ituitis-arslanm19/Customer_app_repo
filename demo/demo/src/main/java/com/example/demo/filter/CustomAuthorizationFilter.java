package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Slf4j
//Authorization' ı sağlamak için geliştirdiğim filtre class'ı
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    //Authorization' ı sağlamak için geliştirdiğim filtre class'ı
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login")){
            //Eğer login sayfasındaysak müdahale etmeden bir sonraki zincire geçiyor

            filterChain.doFilter(request,response);


        }
        else{

            String authorizationHeader = request.getHeader(AUTHORIZATION);  log.info(authorizationHeader);
           try {
               if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                   //Login sayfasında değilsek ve geçerli bi authorization headerımız mevcut ise aynı algoritmayla yeni token yaratılıp security contexte kaydediliyor ardından işleme zincirleme şekilde devam ediliyor
                   log.debug("Authorization process is starting");
                   String token = authorizationHeader.substring("Bearer ".length());
                   Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                   JWTVerifier verifier = JWT.require(algorithm).build();
                   DecodedJWT decodedJWT = verifier.verify(token);
                   String username = decodedJWT.getSubject();
                   String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                   Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
                   stream(roles).forEach(role ->
                   {authorities.add(new SimpleGrantedAuthority(role
                   ));});
                   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,authorities);
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                   filterChain.doFilter(request,response);
                   log.debug("Authorization process is completed");
               }
               else{

                   filterChain.doFilter(request,response);
               }
           }
            catch(Exception e){
               log.error("Error:{}",e.getMessage());






















               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               
               

            }








        }
    }
}

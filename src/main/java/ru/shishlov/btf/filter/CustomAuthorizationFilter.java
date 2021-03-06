package ru.shishlov.btf.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shishlov.btf.components.JwtTokenHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    public CustomAuthorizationFilter(JwtTokenHelper jwtTokenHelper){
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login")){
            filterChain.doFilter(request, response);
        }else{
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader != null){
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                try {
                    DecodedJWT decodedJWT = verifier.verify(authorizationHeader);
                    String username = decodedJWT.getSubject();
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, new HashSet<>());
                    SecurityContextHolder.getContext().setAuthentication(token);
                    filterChain.doFilter(request, response);
                }catch (TokenExpiredException e){
                   response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }else{
                filterChain.doFilter(request, response);
            }
        }
    }
}

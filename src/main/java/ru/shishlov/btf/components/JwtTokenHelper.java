package ru.shishlov.btf.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class JwtTokenHelper {

    public void setToken(String userName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        String access_token = JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60*1000))
                .withIssuer(request.getContextPath())
                .sign(algorithm);
        Map<String, String> answer = new HashMap<>();
        answer.put("token", access_token);
        ObjectMapper obj = new ObjectMapper();
        response.setContentType(APPLICATION_JSON_VALUE);
        obj.writeValue(response.getOutputStream(), answer);
    }
}

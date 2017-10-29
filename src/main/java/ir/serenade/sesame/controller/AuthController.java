package ir.serenade.sesame.controller;

import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;

    @RequestMapping("/api/security/verify")
    public @ResponseBody
    Response verify() {
        return new Response(200,ConstantResponse.success);
    }
}

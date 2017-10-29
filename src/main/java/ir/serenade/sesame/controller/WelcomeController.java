package ir.serenade.sesame.controller;

import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @RequestMapping("/hello")
    Response hello() {
        return new Response(200, ConstantResponse.success);
    }
}

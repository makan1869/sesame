package ir.serenade.sesame.controller;

import io.swagger.annotations.ApiOperation;
import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
    @ApiOperation(value = "Welcome",response = Response.class)
    @RequestMapping(path = "/api/welcome",method = RequestMethod.GET)
    public Response greeting() {
        return new Response(200, ConstantResponse.success);
    }

}

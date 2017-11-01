package ir.serenade.sesame.controller;

import io.swagger.annotations.ApiOperation;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.repository.UserRepository;
import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import ir.serenade.sesame.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.SendResult;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "api/register", method = RequestMethod.POST)
    public @ResponseBody
    Response register(@RequestBody String phoneNumber) {
        User user = new User();
        user.setUsername(phoneNumber);
        user.setPassword("107894");
        System.out.println(user);
        if (user != null) {
            userRepository.save(user);
        }else {
            System.out.println(ConstantResponse.customer_already_exist);
        }
        //username = phoneNumber
        //password = password
        //srcNumber =98200002020
        //body = GenerateRandomNumber
        //desNo = phoneNumber
       String uri =" https://api.wsdltophp.com/rest/request/v1/send/53d5dbf25871fddb417ca10105ee06ba2022f2a2/846/1.json";

        return new Response(200, phoneNumber);
    }

    @ApiOperation(value = "Verify user ", response = Response.class)
    @RequestMapping(path = "/api/security/verify", method = RequestMethod.GET)
    public @ResponseBody
    Response verify() {
        return new Response(200, ConstantResponse.success);
    }
}

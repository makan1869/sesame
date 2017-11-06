package ir.serenade.sesame.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.ApiOperation;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.repository.UserRepository;
import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import ir.serenade.sesame.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "api/register", method = RequestMethod.POST)
    public @ResponseBody
    Response register(@RequestParam("username") String phoneNumber) {
        User newUser = new User();
        newUser.setUsername(phoneNumber);
        Random random = new Random();
        int randomNumber = random.nextInt(1000000);
        newUser.setPassword(randomNumber + "");
        userRepository.save(newUser);
        try {
            HttpResponse<String> res = Unirest.post("http://my.candoosms.com/services/index.php")
                    .header("Content-Type", "text/xml; charset=UTF-8")
                    .body("<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:SMSAPIwsdl\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "   <soapenv:Header/>\n" +
                            "   <soapenv:Body>\n" +
                            "      <urn:Send soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n" +
                            "         <username xsi:type=\"xsd:string\">aminmvno</username>\n" +
                            "         <password xsi:type=\"xsd:string\">C8n0123</password>\n" +
                            "         <srcNumber xsi:type=\"xsd:string\">98200002020</srcNumber>\n" +
                            "         <body xsi:type=\"xsd:string\">" + randomNumber + "</body>\n" +
                            "         <destNo xsi:type=\"urn:Destinations\" soapenc:arrayType=\"xsd:string[1]\">\n" +
                            "         \t<item>" + phoneNumber + "</item>\n" +
                            "         </destNo>\n" +
                            "         <flash xsi:type=\"xsd:string\">0</flash>\n" +
                            "      </urn:Send>\n" +
                            "   </soapenv:Body>\n" +
                            "</soapenv:Envelope>").asString();

            System.out.println(res.getBody());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new Response(200, ConstantResponse.yor_registration_code_is + randomNumber);
    }

    @ApiOperation(value = "Verify user ", response = Response.class)
    @RequestMapping(path = "/api/security/verify", method = RequestMethod.GET)
    public @ResponseBody
    Response verify() {
        return new Response(200, ConstantResponse.success);
    }
}

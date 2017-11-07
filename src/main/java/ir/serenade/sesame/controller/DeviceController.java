package ir.serenade.sesame.controller;

import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.repository.DeviceRepository;
import ir.serenade.sesame.repository.UserRepository;
import ir.serenade.sesame.response.ConstantResponse;
import ir.serenade.sesame.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;

    Response response = new Response(-200, ConstantResponse.defualt);
    @ApiOperation(value = "List of device",response = Response.class)
    @RequestMapping(path = "/api/security/device/list",method = RequestMethod.GET)
    public @ResponseBody
    Response getListOfDevices() {
        List<Device> devices = deviceRepository.findAll();
        response = new Response(200, devices.toString());
        return response;
    }
    @ApiOperation(value = "Remove device by uuid",response = Response.class)
    @RequestMapping(path = "/api/security/device/remove/{deviceUuid}", method =RequestMethod.GET)
    public @ResponseBody
    Response removeDevice(@PathVariable("deviceUuid") String uuid, @RequestHeader(value = "Authorization") String Jwt) {
        Device device = deviceRepository.findDeviceByUuid(uuid);
        String token =
                Jwts.parser().setSigningKey(SECRET).parseClaimsJws(Jwt.replace(TOKEN_PREFIX, "")).getBody().getSubject();
        String[] tokens = token.split(":");
        String username = tokens[0];
        String deviceUuid = tokens[1];
        User user = userRepository.findByUsername(username);
        user.getRoles().forEach(
                role -> {
                    if (device == null) {
                        response = new Response(404, ConstantResponse.uuid_is_not_exist);
                    } else if (role.getName().equals("ROLE_ADMIN")) {
                        deviceRepository.deleteByUuid(uuid);
                        response = new Response(200, ConstantResponse.device_deleted);
                    } else {
                        if (deviceUuid == uuid) {
                            deviceRepository.deleteByUuid(uuid);
                            response = new Response(200, ConstantResponse.device_deleted);
                        }
                    }
                }
        );
        return response;
    }
}

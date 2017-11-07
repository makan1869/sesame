package ir.serenade.sesame.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.User;
import ir.serenade.sesame.service.TokenAuthenticationService;
import ir.serenade.sesame.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UserService userService;

    private TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(String url, UserService userService, AuthenticationManager authManager, TokenAuthenticationService tokenAuthenticationService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userService = userService;
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper()
                .readValue(req.getInputStream(), AccountCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        String ua = req.getHeader("User-Agent");
        Parser uaParser = new Parser();
        Client userAgentInfo = uaParser.parse(ua);
        Device device = new Device();
        device.setDeviceName(userAgentInfo.device.family);
        if (auth instanceof User) {
            device.setUser((User) auth);
        } else if (auth.getPrincipal() instanceof User) {
            device.setUser((User) auth.getPrincipal());
        } else {
            User user = userService.findUserByUsername(auth.getPrincipal().toString());
            device.setUser(user);
        }
        device.setDeviceOS(userAgentInfo.os.family);
        device.setDeviceType(userAgentInfo.userAgent.family);
        device.setUuid(UUID.randomUUID().toString());
        device = userService.saveDevice(device);
        String username = req.getHeader("username");
        String confirmCode = req.getHeader("confirmCode");
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        User user = userService.findUserByUsername(username);
        Date createDate = user.getCreateDate();
        System.out.println(createDate + "  " + currentDate);
        long timeDiff = currentDate.getTime() - createDate.getTime();
        long minutesInMilli = 1000 * 60;
        long different = timeDiff / minutesInMilli;
        System.out.println(different);
        if (user.getPassword().equals(confirmCode)) {
            if (different <= 5) {
                tokenAuthenticationService
                        .addAuthentication(res, auth.getName(), device.getUuid());
            }
        }
    }
}
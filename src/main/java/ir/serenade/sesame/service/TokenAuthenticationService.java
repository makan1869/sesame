package ir.serenade.sesame.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ir.serenade.sesame.domain.entity.Device;
import ir.serenade.sesame.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationService {

    @Autowired
    UserService userService;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private Long EXPIRATION;

    @Value("${jwt.token.prefix}")
    private String TOKEN_PREFIX;

    @Value("${jwt.header.name}")
    private String HEADER_STRING;

    public void addAuthentication(HttpServletResponse res, String username, String uuid) {

        String JWT = Jwts.builder()
                .setSubject(username + ":" + uuid)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            // parse the token.
            String subject = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            String[] tokens = subject.split(":");
            if (tokens.length == 2) {
                String username = tokens[0];
                String deviceId = tokens[1];
                User user = userService.findUserByUsername(username);
                Device device = userService.findDeviceByUserAndUuid(user, deviceId);
                return device != null ?
                        new UsernamePasswordAuthenticationToken(username, null, emptyList()) :
                        null;
            }
        }
        return null;
    }
}
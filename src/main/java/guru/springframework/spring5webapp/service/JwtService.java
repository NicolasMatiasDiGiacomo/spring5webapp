package guru.springframework.spring5webapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    public static final String BEARER = "Bearer ";
    public static final String USER = "user";
    public static final String ROLES = "roles";
    public static final String ISSUER = "spring5-web-app";
    public static final String SECRET = "spring5-web-app-secret";
    public static final int EXPIRES_IN_MILISECOND = 3600000;

    public String createToken(String user, List<String> roles) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_IN_MILISECOND))
                .withClaim(USER, user)
                .withArrayClaim(ROLES, roles.toArray(new String[0]))
                .sign(Algorithm.HMAC256(SECRET));
    }

    public boolean isBearer(String authorization) {
        return authorization != null && authorization.startsWith(BEARER) && authorization.split("\\.").length == 3;
    }

    private DecodedJWT verify(String authorization) throws RuntimeException {
        if (!isBearer(authorization)) {
            throw new RuntimeException("It isn't Bearer");
        }

        try {
            return JWT.require(Algorithm.HMAC256(SECRET))
                    .withIssuer(ISSUER).build()
                    .verify(authorization.substring(BEARER.length()));
        } catch (Exception e) {
            throw new RuntimeException("JWT wrong " + e.getMessage());
        }
    }

    public String getUser(String authorization) throws RuntimeException {
        return this.verify(authorization).getClaim(USER).asString();
    }

    public List<String> getRoles(String authorization) throws RuntimeException {
        return Arrays.asList(this.verify(authorization).getClaim(ROLES).asArray(String.class));
    }
}

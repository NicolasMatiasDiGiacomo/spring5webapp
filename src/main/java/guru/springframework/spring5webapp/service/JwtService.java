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

    public String createToken(String user, List<String> roles) {
        return JWT.create()
                .withIssuer("spring5-web-app")
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
                .withClaim("user", user)
                .withArrayClaim("roles", roles.toArray(new String[0]))
                .sign(Algorithm.HMAC256("spring5-web-app-secret"));
    }

    public boolean isBearer(String authorization) {
        return authorization != null && authorization.startsWith("Bearer") && authorization.split("\\.").length == 3;
    }

    private DecodedJWT verify(String authorization) throws RuntimeException {
        if (!isBearer(authorization)) {
            throw new RuntimeException("It isn't Bearer");
        }

        try {
            return JWT.require(Algorithm.HMAC256("spring5-web-app-secret"))
                    .withIssuer("spring5-web-app").build()
                    .verify(authorization.substring("Bearer".length()));
        } catch (Exception e) {
            throw new RuntimeException("JWT wrong " + e.getMessage());
        }
    }

    public String getUser(String authorization) throws RuntimeException {
        return this.verify(authorization).getClaim("user").asString();
    }

    public List<String> getRoles(String authorization) throws RuntimeException {
        return Arrays.asList(this.verify(authorization).getClaim("roles").asArray(String.class));
    }
}

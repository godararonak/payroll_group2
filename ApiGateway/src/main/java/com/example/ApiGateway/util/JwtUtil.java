package com.example.ApiGateway.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {


    public String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }

    public void validateToken(final String token) {
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build()
                    .parse(token);
        }catch (MalformedJwtException exception){
            throw new RuntimeException("invalid jwt token");
        }catch (ExpiredJwtException exception){
            throw new RuntimeException("expiration jwt token");
        }catch (UnsupportedJwtException exception){
            throw new RuntimeException("unsupported jwt token");
        }catch (IllegalArgumentException exception){
            throw new RuntimeException("jwt claims string in null or empty");
        }
    }

}

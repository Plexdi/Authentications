package authentication.com.Authentications.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {
    
    //JWTUtils requires a key
    @Value("${jwt.secret}") 
    private String jwtsecret; 

    //create a method to generate token 
    public String generateToken(String username){

        //use the builder to define what's in the token 
        return Jwts.builder()
            .setSubject(username) //this token belong to username 
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) //expiry date of the token 
            .signWith(SignatureAlgorithm.HS512, jwtsecret) //signing with the token using HS512 token 
            .compact(); //turn into a string
    }

    public String extractUsername(String token){
        return Jwts.parser()
            .setSigningKey(jwtsecret)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }
    
}

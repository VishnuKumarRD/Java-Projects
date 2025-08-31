package rdison.Begining.util;


import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET="Vishnu Kumar-R D Itz._.Vishnu._25 vishnukumarvkrd@gmail.com";
    private final long EXPIRATION=1000*60*10;//1 min expire time
    private final Key secretKey= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));//change in byte format

    //generate token with the help of regitered Email
    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)//letter ra seal pandrom ( digital signature with the help of secretKey
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()//seal la open pandrom
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //determine validate the Generated token
    public boolean validateJwtToken(String token){
        try{
            extractEmail(token);//pass the jwt token here
//            Jwts.parserBuilder()//seal la open pandrom
//                    .setSigningKey(secretKey)
//                    .build()
//                    .parseClaimsJws(token) //Evaluate the validate token or not
//                    .getBody()
//                    .getSubject();
            return true;
        }catch(JwtException exception){
            return false;
        }
    }



}

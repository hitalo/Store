package com.hit.store.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.hit.store.models.people.User;

import java.util.Date;

@Service
public class JwtUtils {


    public String extractUsername(String token) {		
    	final Claims claims = extractAllClaims(token);
        return claims.get("login").toString();
    }

	
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Constants.API_SECRET_KEY).parseClaimsJws(token).getBody();
    }


    private Boolean isTokenExpired(String token) {
    	final Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    
    public String generateToken(User user) {
    	long timestamp = System.currentTimeMillis();
		String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
				.claim("userId", user.getId())
				.claim("login", user.getLogin())
				.claim("name", user.getPeople().getName())
				.compact();
		return token;
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

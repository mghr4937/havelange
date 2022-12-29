package com.mesti.havelange.configs.security;

import com.mesti.havelange.models.users.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils{

    private static final String BEARER = "Bearer ";
    private static final String MY_SECRET_KEY = "mySecretKey";
    private static final String AUTHORITIES = "authorities";
    private static final String SOFTTEK_JWT = "softtekJWT";

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(Role.ROLE_ADMIN.getDisplayName());

            String token = Jwts
                    .builder()
                    .setId(SOFTTEK_JWT)
                    .setSubject(username)
                    .claim(AUTHORITIES,
                            grantedAuthorities.stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 600000))
                    .signWith(SignatureAlgorithm.HS512,
                            MY_SECRET_KEY.getBytes()).compact();

            return BEARER.concat(token);
        }
}

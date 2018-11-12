package br.com.seguranca.jwtotp.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.seguranca.jwtotp.dto.JwtDTO;
import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class TokenAuthenticationService {

    // EXPIRATION_TIME = 20 minutos
    private static final long EXPIRATION_TIME = 12_000_00;
    private static final String SECRET = "tM4LP=BxJRK;$M*,=bMM?!!~,ncYC~7}";
    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String ROLES = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

    static void addAuthentication(HttpServletResponse response, Authentication authentication) throws IOException {
        String role = authentication.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElseThrow(IOException::new);
        String jwtBody = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(ROLES, role)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        JwtDTO token = new JwtDTO(TOKEN_PREFIX + " " + jwtBody);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(new Gson().toJson(token));
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token != null) {
            final Claims body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String user = body.getSubject();

            final Collection<SimpleGrantedAuthority> authorities =
                    Arrays.stream(body.get(ROLES).toString().split(","))
                            .map(s -> new SimpleGrantedAuthority(ROLE_PREFIX + s))
                            .collect(Collectors.toList());

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
        }
        return null;
    }

}
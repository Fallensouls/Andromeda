package hello.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

//    @Value("${jwt.secret}")
    private static final String secret = "lajimk"; // 签名所用的密钥

    private static final String refresh_secret = "lajikaizi"; // 用于refresh_token的密钥

//    @Value("${jwt.expiration}")
    private static final Long expiration = 3600L; // Token的有效时长,单位为秒
    private static final Long refresh_expiration = 604800L;

    // 从Token中获取用户名
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    // 从Token中获取用户名
    public String getUsernameFromRefreshToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromRefreshToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    // 从Token中获取生成时间
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getCreatedDateFromRefreshToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromRefreshToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    // 从Token中获取过期时间
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    // 从Token中获取过期时间
    public Date getExpirationDateFromRefreshToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromRefreshToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }



    // 从Token中获取Claims
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // 从Token中获取Claims
    private Claims getClaimsFromRefreshToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(refresh_secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    // 生成过期时间
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Date generateRefreshExpirationDate() {
        return new Date(System.currentTimeMillis() + refresh_expiration * 1000);
    }

    // 判断Token是否过期，过期的Token应失效
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration == null;
    }

    private Boolean isRefreshTokenExpired(String token) {
        Date expiration = getExpirationDateFromRefreshToken(token);
        return expiration == null;
    }


    // 判断Token签发后密码有没有被修改，如有则返回True，Token应失效
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(JwtUser jwtUser) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, jwtUser.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    // 生成Token
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateRefreshToken(JwtUser jwtUser){
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, jwtUser.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateRefreshToken(claims);
    }

    // 生成RefreshToken
    private String generateRefreshToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateRefreshExpirationDate())
                .signWith(SignatureAlgorithm.HS512, refresh_secret)
                .compact();
    }


    // 判断Token是否应刷新，仅当RefreshToken没有过期且密码没有重置时可以刷新
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromRefreshToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && !isRefreshTokenExpired(token);
    }

    // 刷新Token
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromRefreshToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        //final Date expiration = getExpirationDateFromToken(token);
        return (username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getUpddate()));
    }
}

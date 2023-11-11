package hyunwoo.baemin.global.jwt;

import hyunwoo.baemin.domain.User.User;
import hyunwoo.baemin.domain.User.UserRole;
import hyunwoo.baemin.repository.User.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret")
    private String secretKey;
    private Key key;

    private final UserRepository userRepository;

    // AccessToken 만료 : 1일
    private static final Long accessTokenExpirationTime = 24 * 60 * 60 * 1000L;

    // RefreshToken 민료 : 30일
    private static final Long refreshTokenExpirationTime = 30 * 24 * 60 * 60 * 1000L;

    // secretKey를 이용해 jwt에 사용할 개인키 생성 -> 빈 등록
    @PostConstruct
    protected void init(){
        byte[] secretKeyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(secretKeyBytes);
    }


    //AccessToken 생성, 아래에서 만든 claim 설정 메서드를 이용해
    private String createAccessToken(User user){
        Claims claims = getClaims(user);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    //AccessToken 과 같은 방식으로 RefreshToken 생성
    private String createRefreshToken(User user){
        Claims claims = getClaims(user);

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 생성
    public TokenDto createToken(User user){
        return TokenDto.builder()
                .accessToken(createAccessToken(user))
                .refreshToken(createRefreshToken(user))
                .build();
    }

    // 만료 됐는지 아닌지 판단
    public boolean validateToken(String token){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token)
                    .getBody().getExpiration().after(new Date());
        } catch (Exception e){ // 만약 getExpiration에서 제대로 된 Date가 없으면 false 반환
            return false;
        }
    }

    // refresh 토큰을 이용해, accessToken 재발급
    public TokenDto reissueAccessToken(String token){
        String email = getEmail(token);
        UserRole role = getRole(token);

        User user = userRepository.findByEmailAndRole(email, role).orElseThrow(RuntimeException::new);

        String accessToken = createAccessToken(user);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(token)
                .build();
    }


    //토큰에서 authentication을 반환
    public Authentication getAuthentication(String token){
        String email = getEmail(token);
        UserRole role = getRole(token);

        User user = userRepository.findByEmailAndRole(email, role).orElseThrow(RuntimeException::new);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(user.getRole().toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails details = new org.springframework.security.core.userdetails.User(email, "", authorities);

        return new UsernamePasswordAuthenticationToken(email, "", authorities);
    }

    // 토큰의 expiration 리턴
    private Date getExpiration(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody().getExpiration();
    }

    // 비공개 claim 정보 생성 , email, role 정보 담겨있음
    private Claims getClaims(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("role", user.getRole());

        return claims;
    }

    //Token에서 email 정보 꺼내기
    private String getEmail(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody().getSubject();
    }

    //Token에서 user role 정보 꺼내기
    private UserRole getRole(String token){
        return UserRole.valueOf((String) Jwts.parserBuilder().setSigningKey(key).build()
                        .parseClaimsJwt(token).getBody().get("role"));
    }

}

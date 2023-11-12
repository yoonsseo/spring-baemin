package hyunwoo.baemin.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import hyunwoo.baemin.domain.User.User;
import hyunwoo.baemin.repository.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String key;
    private final UserRepository userRepository;

    private Long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L; // 3시간
    private Long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L; // 7일

    @Autowired
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, UserRepository userRepository) {
        this.key = secretKey;
        this.userRepository = userRepository;
    }

    // 유저 정보 받아와서 토큰 발급
    public JwtTokenDto generateTokenDto(User user){
        long now = (new Date().getTime());

        // accessToken 생성 , e-mail 이 subject, 유저 id 포함
        String accessToken = JWT.create()
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(key));


        // refreshToken 생성, refresh는 만료시간만 포함
        String refreshToken = JWT.create()
                .withExpiresAt(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(key));

        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType("Bearer")
                .build();
    }

    // authentication 가져오기, accessToken 사용
    public Authentication getAuthentication(String token){
        Long userId = JWT.require(Algorithm.HMAC256(key)).build().verify(token).getClaim("id").asLong();

        User user = userRepository.findById(userId).orElseThrow();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());

    }

    //token 검증
    public boolean validateToken(String token){
        try {
            JWT.require(Algorithm.HMAC256(key)).build().verify(token);
            return true;
        } catch (SignatureVerificationException e){
            throw e;
        } catch (TokenExpiredException e){
            throw e;
        } catch (AlgorithmMismatchException e){
            throw e;
        } catch (JWTVerificationException e){
            throw e;
        }
    }

}

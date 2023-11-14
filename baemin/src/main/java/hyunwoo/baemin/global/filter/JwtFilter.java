package hyunwoo.baemin.global.filter;

import hyunwoo.baemin.global.jwt.JwtTokenProvider;
import hyunwoo.baemin.global.redis.RedisService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰 꺼내기
        String jwt = resolveToken(request);

        // 유효성 검사 후 토큰의 인증정보를 securityContext에 저장
        if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else logger.debug("jwt 토큰이 유효하지 않음");
        filterChain.doFilter(request, response);
    }

    // request 헤더에서 토큰을 꺼내는 메서드
    private String resolveToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if (StringUtils.hasText(token) && token.startsWith("Bearer")){
            return token.replace("Bearer ", "");
        }

        return null;
    }
}

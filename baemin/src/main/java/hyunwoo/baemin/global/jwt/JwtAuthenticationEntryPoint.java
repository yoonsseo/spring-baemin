package hyunwoo.baemin.global.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 인증되지 않은 경우 401 반환
    // 서버의 응답을 클라이언트에게 더욱 정확하게 제어할 수 있음
    // commence 메서드를 구현하면, 인증이 필요한 리소스에 접근 시에 어떻게 응답할지를 정할 수 있음
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

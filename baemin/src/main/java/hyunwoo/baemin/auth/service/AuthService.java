package hyunwoo.baemin.auth.service;

import hyunwoo.baemin.auth.dto.LoginDto;
import hyunwoo.baemin.auth.dto.ReissueDto;
import hyunwoo.baemin.auth.dto.SingupDto;
import hyunwoo.baemin.domain.Message;
import hyunwoo.baemin.domain.User.User;
import hyunwoo.baemin.domain.User.UserRole;
import hyunwoo.baemin.global.jwt.JwtTokenDto;
import hyunwoo.baemin.global.jwt.JwtTokenProvider;
import hyunwoo.baemin.global.redis.RedisService;
import hyunwoo.baemin.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final RedisService redisService;

    public ResponseEntity<Message> signup(SingupDto singupDto) {
        //signupDto의 정보들로 유저 생성

        //이미 있는 회원인지 조회
        if (userRepository.findByEmail(singupDto.getEmail()).isPresent()){
            Message message = new Message();
            return new ResponseEntity<Message>(message, HttpStatusCode.valueOf(400));
        }

        User user = User.builder()
                .email(singupDto.getEmail())
                .password(passwordEncoder.encode(singupDto.getPassword())) // password 인코딩
                .phoneNum(singupDto.getPhoneNum())
                .role(UserRole.valueOf("NORMAL"))
                .name(singupDto.getName())
                .build();
        userRepository.save(user);

        Message message = new Message();
        return new ResponseEntity<Message>(message, HttpStatusCode.valueOf(202));
    }

    public ResponseEntity<Message> login(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

        if (optionalUser.isEmpty()){ // 존재하지 않는 유저인 경우
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }

        User user = optionalUser.get();
        if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())){
            JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(user);
            redisService.setValue(jwtTokenDto.getRefreshToken(), user.getId().toString(), 1000 * 60 * 60 * 24 * 7L);
            Message message = new Message();
            message.setData(jwtTokenDto);
            message.setMessage("로그인 성공");
            return new ResponseEntity<Message>(message, HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(400));
    }

    public ResponseEntity<Message> reissue(ReissueDto reissueDto) {
        String refreshToken = reissueDto.getRefreshToken();
        if (redisService.getValue(refreshToken).equals("Deprecated") ||
                !tokenProvider.validateToken(refreshToken)){ // refreshToken의 validity 검사
            return new ResponseEntity<Message>(HttpStatusCode.valueOf(404));
        }

        Long userId = Long.valueOf(redisService.getValue(refreshToken));
        User user = userRepository.findById(userId).get();

        JwtTokenDto jwtTokenDto = tokenProvider.generateTokenDto(user);
        //이미 사용한 refresh 토큰은 Deprecated로 설정
        redisService.setValue(refreshToken, "Deprecated", 1000 * 60 * 60 * 24 * 7L);
        redisService.setValue(jwtTokenDto.getRefreshToken(),userId.toString(), 1000 * 60 * 60 * 24 * 7L);

        Message message = new Message();
        message.setMessage("재발급 성공");
        message.setData(jwtTokenDto);
        return new ResponseEntity<Message>(message, HttpStatusCode.valueOf(201));
    }
}

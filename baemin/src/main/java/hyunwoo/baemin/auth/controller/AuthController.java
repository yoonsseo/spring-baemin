package hyunwoo.baemin.auth.controller;

import hyunwoo.baemin.auth.dto.LoginDto;
import hyunwoo.baemin.auth.dto.ReissueDto;
import hyunwoo.baemin.auth.dto.SingupDto;
import hyunwoo.baemin.auth.service.AuthService;
import hyunwoo.baemin.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    // signup, login, logout, reissue
    private final AuthService authService;

    @PostMapping("/signup/")
    public ResponseEntity<Message> signup(@RequestBody SingupDto singupDto){
        return authService.signup(singupDto);
    }

    @PostMapping("/login/")
    public ResponseEntity<Message> login(@RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }

    @PostMapping("/reissue/")
    public ResponseEntity<Message> reissue(@RequestBody ReissueDto reissueDto){
        return authService.reissue(reissueDto);
    }

}

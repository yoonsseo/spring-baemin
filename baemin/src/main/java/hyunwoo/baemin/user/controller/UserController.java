package hyunwoo.baemin.user.controller;

import hyunwoo.baemin.domain.Message;
import hyunwoo.baemin.global.jwt.CustomUserDetails;
import hyunwoo.baemin.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // 정보 조회,
    @RequestMapping("/my-info")
    public ResponseEntity<Message> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails){
        Long id = userDetails.getUser().getId();
        return userService.getUserInfo(id);
    }
}

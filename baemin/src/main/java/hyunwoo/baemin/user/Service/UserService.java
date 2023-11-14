package hyunwoo.baemin.user.Service;

import hyunwoo.baemin.domain.Message;
import hyunwoo.baemin.user.Dto.UserInfoDto;
import hyunwoo.baemin.domain.User.User;
import hyunwoo.baemin.domain.User.UserAddress;
import hyunwoo.baemin.repository.User.UserAddressRepository;
import hyunwoo.baemin.repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;

    public ResponseEntity<Message> getUserInfo(Long userId){
        User user = userRepository.findById(userId).get();
        List<UserAddress> addresses = userAddressRepository.findAllByUser(user);
        UserAddress address = addresses.get(user.getNowAddress());

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .id(user.getId())
                .name(user.getName())
                .address(address)
                .phoneNum(user.getPhoneNum())
                .build();

        Message message = new Message();
        message.setData(userInfoDto);
        message.setMessage("유저 정보");
        return new ResponseEntity<Message>(message, HttpStatusCode.valueOf(201));
    }
}

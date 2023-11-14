package hyunwoo.baemin.user.Dto;

import hyunwoo.baemin.domain.User.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long id;
    private String name;
    private UserAddress address;
    private String phoneNum;
}

package hyunwoo.baemin.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingupDto {
    private String email;
    private String password;
    private String name;
    private String phoneNum;
    private String address;

    @Builder
    public SingupDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

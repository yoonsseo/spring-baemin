package hyunwoo.baemin.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueDto {
    private String refreshToken;

}

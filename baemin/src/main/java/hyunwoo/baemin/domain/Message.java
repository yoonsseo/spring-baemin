package hyunwoo.baemin.domain;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class Message {

    private HttpStatusCode httpStatusCode;
    private String message;
    private Object data;

    public Message() {
        this.httpStatusCode = HttpStatusCode.valueOf(400);
        this.message = null;
        this.data = null;
    }
}

package hyunwoo.baemin.domain;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class Message {

    private String message;
    private Object data;

    public Message() {
        this.message = null;
        this.data = null;
    }
}

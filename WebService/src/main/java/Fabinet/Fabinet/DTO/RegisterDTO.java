package Fabinet.Fabinet.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class RegisterDTO {

    //AJAX측의 변수명과 같아야 받아진다
    private String userName;
    private String userID;
    private String userPW;
    private String userPW2;
    private String userEmail;
    private String userTel;
    private Blob u_img;
}

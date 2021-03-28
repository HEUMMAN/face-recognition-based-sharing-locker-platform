package Fabinet.Fabinet.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class RegisterDTO {

    //AJAX측의 변수명과 같아야 받아진다
    private String u_name;
    private String u_id;
    private String u_pw;
    private String u_pw2;
    private String u_tel;
    private String u_email;
    private Blob u_img;
}

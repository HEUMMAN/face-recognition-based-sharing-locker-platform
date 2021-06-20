package com.jongsul.fabinetgradle.DTO;

import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
public class RegisterDTO {

    private String userName;
    private String userID;
    private String userPW;
    private String userPW2;
    private String userEmail;
    private String userTel;
    private Blob u_img;

    @Override
    public String toString() {
        return "\nName: "+userName
                +"\nloginID: "+userID
                +"\nloginPassword: "+userPW
                +"\ntel: "+userTel
                +"\nEmail: "+userEmail;
    }
}

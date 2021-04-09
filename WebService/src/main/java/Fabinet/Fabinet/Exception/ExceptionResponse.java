package Fabinet.Fabinet.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//예외에 대한 정보를 표시하기 위한 POJO
@Data
@AllArgsConstructor //모든 인자가진 생성자 생성 롬복
@NoArgsConstructor  //인자 없는 생성자 생성 롬복
public class ExceptionResponse {
    private Date timestamp;
    private String message;
    private String details;
}

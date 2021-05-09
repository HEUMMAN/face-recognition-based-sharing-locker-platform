package Jongsul.fabinetmaven.ControllerTest;

import Jongsul.fabinetmaven.DTO.LoginDTO;
import Jongsul.fabinetmaven.DTO.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void 회원가입() throws Exception {
        //given
        RegisterDTO inputMember = new RegisterDTO();
        inputMember.setUserID("ddff");
        inputMember.setUserPW("vvbb");
        inputMember.setUserPW2("vvbb");
        inputMember.setUserEmail("fffd@naver.com");
        inputMember.setUserName("hong");
        inputMember.setUserTel("010-1234-5678");
        String content = objectMapper.writeValueAsString(inputMember); //Object to Json

        //when

        //then
        mvc.perform(post("/register")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }


    @Test
    public void 로그인() throws Exception {
        //given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserID("zxcv");
        loginDTO.setUserPW("zxcv");
        String content = objectMapper.writeValueAsString(loginDTO); //Object to Json

        //when

        //then
        mvc.perform(post("/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}

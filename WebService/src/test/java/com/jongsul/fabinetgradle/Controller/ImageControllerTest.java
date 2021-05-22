package com.jongsul.fabinetgradle.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class ImageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockHttpSession session;
    protected String content = null;

    @Before
    public void setup() throws JsonProcessingException {
        session = new MockHttpSession();
        session.setAttribute("loginMemberId", "zxcv");

        File imageFile = new File("C:\\Users\\lsh97\\Desktop\\lshimg\\pby.jpg");
        content = objectMapper.writeValueAsString(imageFile);
    }

    @After
    public void clear(){
        session.clearAttributes();
        session = null;
    }

    @Test
    public void 이미지_업로드() throws Exception {

    }


    @Test
    public void 이미지_다운로드() throws Exception {

    }
}

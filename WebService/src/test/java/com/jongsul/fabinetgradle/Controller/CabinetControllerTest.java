package com.jongsul.fabinetgradle.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CabinetControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;

    @Before
    public void setup(){
        session = new MockHttpSession();
        session.setAttribute("loginMemberId", "zxcv");

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @After
    public void clear(){
        session.clearAttributes();
        session = null;
    }

    @Test
    public void 사용중인_사물함_목록조회() throws Exception {
        mvc.perform(get("/bill/list")
                .session(session))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }


    @Test
    public void 사물함_사용하기() throws Exception {
        mvc.perform(post("/bill/chooseCabinet")
                .param("select1","D")
                .param("select2","1")
                .param("select3","5")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    public void 모든_사용내역_가져오기() throws Exception {
        //when
        mvc.perform(get("/bill/cabinets"))
                .andExpect(status().isOk());
    }

    @Test
    public void 결제금액_정산() throws Exception {

    }

    @Test
    public void 결제API호출() throws Exception {

    }
}

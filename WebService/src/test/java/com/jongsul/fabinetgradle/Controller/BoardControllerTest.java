package com.jongsul.fabinetgradle.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jongsul.fabinetgradle.DTO.BoardDTO;
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
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    private final String title = "This is test title5";
    private final String content = "This is test content5";

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
    public void 게시글_전체_불러오기() throws Exception {
        //when
        mvc.perform(get("/board/post"))
                .andExpect(status().isOk());
    }

    @Test
    public void 게시글_작성하기() throws Exception {
        //given
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setTitle(title);
        boardDTO.setContent(content);
        String content = objectMapper.writeValueAsString(boardDTO); //Object to Json

        //when

        //then
        mvc.perform(post("/board/post")
                .session(session)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    @Test
    public void 게시글_1개_불러오기() throws Exception {
        mvc.perform(get("/board/1"))
                .andExpect(status().isOk());
    }
}

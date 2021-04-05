package Fabinet.Fabinet.Controller;

import Fabinet.Fabinet.DTO.BoardDTO;
import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Domain.Member;
import Fabinet.Fabinet.Service.BoardService;
import Fabinet.Fabinet.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    //게시글 불러오기
    @ResponseBody
    @GetMapping("/list")
    public List showEntireBoard(HttpServletResponse response){
        log.info("게시글 불러오기");
        List<Board> boards = boardService.findBoards();
        return boards;
    }

    //게시글 작성 DB등록
    @PostMapping("/post")
    public void createBoard(@RequestBody BoardDTO boardDTO, HttpServletRequest request) throws IOException {

        log.info("새로운 게시글 DB등록 시작");
        HttpSession session = request.getSession();
        String sessionId = (String)session.getAttribute("loginMemberId");
        log.info("유저ID: "+sessionId);
        LocalDateTime now = LocalDateTime.now();    //날짜+시간 형식

        Member member = memberService.findOne(sessionId);
        System.out.println("Member name: "+member.getName());
        System.out.println("Member loginID: "+member.getLoginId());

        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setAuthor(sessionId);
        board.setDate(now);
        board.setMember(member);

        System.out.println("제목: "+boardDTO.getTitle());
        System.out.println("내용: "+boardDTO.getContent());
        System.out.println("시간: "+now);
        System.out.println("작성자: "+sessionId);
        System.out.println("멤버객체: "+member);

        String result = boardService.createBoard(board);
        log.info("["+result+"]에 대한 게시글 등록 완료");
    }
}

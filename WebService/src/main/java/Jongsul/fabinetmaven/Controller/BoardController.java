package Jongsul.fabinetmaven.Controller;


import Jongsul.fabinetmaven.DTO.BoardDTO;
import Jongsul.fabinetmaven.Domain.Board;
import Jongsul.fabinetmaven.Domain.Member;
import Jongsul.fabinetmaven.Exception.BoardNotFoundException;
import Jongsul.fabinetmaven.Service.BoardService;
import Jongsul.fabinetmaven.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
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
    @GetMapping("/post")
    public List showEntireBoard(HttpServletResponse response){
        log.info("게시글 불러오기");
        List<Board> boards = boardService.findBoards();
        return boards;
    }

    //게시글 1개 불러오기
    @GetMapping("/{id}")
    public Board showOneBoard(@PathVariable int id, HttpServletResponse response){
        log.info(id+"번 게시글 불러오기");
        Board board = boardService.findOne(id);
        //존재하지 않는 게시물 조회시 예외 상태코드 반환
        if (board == null) {
            throw new BoardNotFoundException(String.format("ID[%s] not found",id));
        }
        return board;
    }

    //게시글 작성 DB등록
    @PostMapping("/post")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO, HttpServletRequest request) throws IOException {

        log.info("새로운 게시글 DB등록 시작");
        HttpSession session = request.getSession();
        String sessionId = (String)session.getAttribute("loginMemberId");
        log.info("유저ID: "+sessionId);
        LocalDateTime now = LocalDateTime.now();    //날짜+시간 형식

        Board board = new Board();
        if(sessionId!=null){
            Member member = memberService.findOne(sessionId);
            System.out.println("Member name: "+member.getName());
            System.out.println("Member loginID: "+member.getLoginId());
            board.setAuthor(sessionId);
            board.setMember(member);
        }
        else{
            board.setAuthor("guest");
            board.setMember(null);
        }
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setDate(now);

        System.out.println("제목: "+boardDTO.getTitle());
        System.out.println("내용: "+boardDTO.getContent());
        System.out.println("시간: "+now);
        System.out.println("작성자: "+sessionId);

        String result = boardService.createBoard(board);
        log.info("["+result+"]에 대한 게시글 등록 완료");

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(board.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

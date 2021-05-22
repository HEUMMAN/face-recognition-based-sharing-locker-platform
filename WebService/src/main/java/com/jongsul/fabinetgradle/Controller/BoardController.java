package com.jongsul.fabinetgradle.Controller;


import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.DTO.BoardDTO;
import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Exception.BoardNotFoundException;
import com.jongsul.fabinetgradle.Service.BoardService;
import com.jongsul.fabinetgradle.Service.MemberService;
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

    //게시글 불러오기
    @GetMapping("/post")
    public ResponseEntity<List<Board>> showEntireBoard(){
        log.info("게시글 불러오기");
        List<Board> boards = boardService.findBoards();
        return ResponseEntity.ok(boards);
    }

    //게시글 1개 불러오기
    @GetMapping("/{id}")
    public ResponseEntity<Board> showOneBoard(@PathVariable int id){
        log.info(id+"번 게시글 불러오기");
        Board board = boardService.findOne(id);
        //존재하지 않는 게시물 조회시 예외 상태코드 반환
        if (board == null) {
            throw new BoardNotFoundException(String.format("ID[%s] not found",id));
        }
        return ResponseEntity.ok(board);
    }

    //게시글 작성 DB등록
    @PostMapping("/post")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO boardDTO, HttpServletRequest request){
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(boardService.createBoard(boardDTO,request).getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}

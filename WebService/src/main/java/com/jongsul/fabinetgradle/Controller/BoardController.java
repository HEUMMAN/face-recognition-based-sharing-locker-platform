package com.jongsul.fabinetgradle.Controller;


import com.jongsul.fabinetgradle.DTO.BoardDTO;
import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
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

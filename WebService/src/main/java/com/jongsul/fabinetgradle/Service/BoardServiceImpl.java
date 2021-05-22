package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Config.MemberInformation;
import com.jongsul.fabinetgradle.DTO.BoardDTO;
import com.jongsul.fabinetgradle.Domain.Board;
import com.jongsul.fabinetgradle.Domain.Member;
import com.jongsul.fabinetgradle.Repository.BoardRepository;
import com.jongsul.fabinetgradle.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final MemberInformation memberInformation;

    @Override
    @Transactional
    public Board createBoard(BoardDTO boardDTO, HttpServletRequest request) {
        log.info("새로운 게시글 생성");
        String userID = memberInformation.getUserName(request);
        log.info("userID: "+userID);
        LocalDateTime now = LocalDateTime.now();    //날짜+시간 형식

        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .date(now)
                .author(userID)     //로그인된 세션이 없다면 인터셉터가 막기때문에 null에대한 처리 안해도됨
                .member(memberRepository.findOne(userID))
                .build();
        boardRepository.save(board);
        return board;
    }

    @Override
    public List<Board> findBoards(){
        return boardRepository.findAll();
    }

    @Override
    public Board findOne(long id) {
        return boardRepository.findOne(id);
    }
}

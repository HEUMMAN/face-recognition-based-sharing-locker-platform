package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.DTO.BoardDTO;
import Fabinet.Fabinet.Domain.Board;
import Fabinet.Fabinet.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public String createBoard(Board board) {
        boardRepository.save(board);
        return board.getTitle();    //저장한 게시글의 제목 반환
    }

    @Override
    public List<Board> findBoards(){
        return boardRepository.findAll();
    }
}

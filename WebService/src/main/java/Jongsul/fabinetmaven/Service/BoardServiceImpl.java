package Jongsul.fabinetmaven.Service;

import Jongsul.fabinetmaven.Domain.Board;
import Jongsul.fabinetmaven.Repository.BoardRepository;
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

    @Override
    public Board findOne(long id) {
        return boardRepository.findOne(id);
    }
}

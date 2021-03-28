package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.DTO.BoardDTO;
import Fabinet.Fabinet.Domain.Board;

import java.util.List;

public interface BoardService {

    public String createBoard(Board board);

    public List<Board> findBoards();
}

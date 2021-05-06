package Jongsul.fabinetmaven.Service;

import Jongsul.fabinetmaven.Domain.Board;

import java.util.List;

public interface BoardService {

    public String createBoard(Board board);

    public List<Board> findBoards();

    public Board findOne(long id);
}

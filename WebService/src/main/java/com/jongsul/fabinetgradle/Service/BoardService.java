package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.Domain.Board;

import java.util.List;

public interface BoardService {

    public String createBoard(Board board);

    public List<Board> findBoards();

    public Board findOne(long id);
}

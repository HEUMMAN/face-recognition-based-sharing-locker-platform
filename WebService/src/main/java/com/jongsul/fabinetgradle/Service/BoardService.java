package com.jongsul.fabinetgradle.Service;

import com.jongsul.fabinetgradle.DTO.BoardDTO;
import com.jongsul.fabinetgradle.Domain.Board;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface BoardService {

    Board createBoard(BoardDTO boardDTO, HttpServletRequest request);
    List<Board> findBoards();
    Board findOne(long id);
}

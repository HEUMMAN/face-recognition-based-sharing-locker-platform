package Jongsul.fabinetmaven.Repository;


import Jongsul.fabinetmaven.Domain.Board;

import java.util.List;

public interface BoardRepository {

    public void save(Board board);

    public List<Board> findAll();

    public Board findOne(long id);
}

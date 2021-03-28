package Fabinet.Fabinet.Service;

import Fabinet.Fabinet.Domain.Board;

import java.util.List;

public interface CabinetService {

    public String calculateBill(Board board);

    long getBill(Object loginMemberId);
}

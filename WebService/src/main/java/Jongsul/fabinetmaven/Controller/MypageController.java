package Jongsul.fabinetmaven.Controller;

import Jongsul.fabinetmaven.DTO.BillDTO;
import Jongsul.fabinetmaven.Domain.Cabinet;
import Jongsul.fabinetmaven.Domain.Member;
import Jongsul.fabinetmaven.Service.BoardService;
import Jongsul.fabinetmaven.Service.CabinetService;
import Jongsul.fabinetmaven.Service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

}

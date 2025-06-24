package com.tenco.blog.board;

import com.tenco.blog.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    //DI 처리
    private final BoardRepository br;

    /**
     * 게시글 작성창 요청
     * 권한체크 = 세션 사용자만 허용
     * @param session
     * @return
     */
    @GetMapping("/board/save-form")
    public String saveForm(HttpSession session) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form"; //세션 정보 없으면 리다이렉트
        }
        return "board/save-form"; //있으면 이동
    }

    //http://localhost:8080/board/save
    //게시글 저장 액션
    //권한체크 = 세션 사용자만 허용
    @PostMapping("/board/save")
    public String save(BoardRequest.saveDTO reqDTO, HttpSession session) {

        try {
        //1.권한체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form"; //세션 정보 없으면 리다이렉트
        }
        //2.유효성 검사
        reqDTO.validate();
        //3.SaveDTO 저장시키기 위해 Board 변환을 해준다
        //Board board = reqDTO.toEntity(sessionUser);
        br.save(reqDTO.toEntity(sessionUser));
        return "redirect:/";

        } catch (Exception e) {
            e.printStackTrace();
            return "board/save-form";
        }
    }


    /**
     * 게시글 상세보기 화면 요청
     * @param id - 게시글 PK
     * @param request (뷰에 데이터 전달)
     * @return detail.mustache
     */
    @GetMapping("/board/{id}")
    public String detail(@PathVariable(name = "id") Long id, HttpServletRequest request) {

        Board board = br.findById(id);
        request.setAttribute("board", board);

        return "board/detail";
    }//detail


    @GetMapping("/")
    public String index(HttpServletRequest request) {

        //1.게시글 목록 조회
        List<Board> boardList = br.findByAll();
        //2.Board 엔티티는 User 엔티티와 연관관계
        //boardList.get(0).getUser().getUsername();//연관관계 호출 확인
        //3.뷰에 데이터 전달
        request.setAttribute("boardList", boardList);

        return "index";
    }//
}//
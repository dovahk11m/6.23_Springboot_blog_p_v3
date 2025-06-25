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


    /* 회원정보 수정 화면 요청
    http://localhost:8080/user/update-form
    GET
     */
    public String updateForm(HttpServletRequest request, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sesseionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }

        request.setAttribute("user", sessionUser);
        return "/user/update-form";
    }









    /* 게시글 수정 화면 요청
    /board/{id}/update-form
    GET
     */
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable(name = "id") Long boardId,
                             HttpServletRequest request, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        Board board = br.findById(boardId);
        if (board == null) {
            throw new RuntimeException("게시글이 없습니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new RuntimeException("수정 권한이 없습니다");
        }
        request.setAttribute("board", board);
        return "/board/update-form";
    }
    /* 수정요청의 흐름
    1.세션에서 회원 정보 불러오기
    2.인증검사
    3.게시글 존재 확인
    4.권한체크
    5.스프링컨테이너 내부에서 뷰리졸버를 통해 머스테치 파일 찾기
     */

    /* 수정 액션 요청
    /board/5/update-form
    POST
     */
    @PostMapping("/board/{id}/update-form")
    public String update(@PathVariable(name = "id")Long boardId,
                         HttpSession session, BoardRequest.UpdateDTO reqDTO) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        reqDTO.validate();
        Board board = br.findById(boardId);
        if (board == null) {
            throw new IllegalArgumentException("게시물이 없습니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new RuntimeException("수정 권한이 없습니다");
        }

        //6 수정 (엔티티 접근해서 상태변경)
        br.updateById(boardId, reqDTO);

        return "redirect:/board/"+boardId;
    }
    /* 수정 액션의 흐름
    1.세션 회원정보 확인
    2.인증검사
    3.유효성검사
    4.게시글 존재 확인
    5.권한체크
    6.수정 & 더티체킹
    7.리다이렉트
     */

    /* 게시글 삭제 요청
    /board/{{board.id}}/delete
    POST
     */
    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable(name = "id") Long id,HttpSession session ,HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            return "redirect:/login-form";
        }
        Board board = br.findById(id);
        if (board == null) {
            throw new IllegalArgumentException("이미 삭제된 게시글입니다");
        }
        if (!board.isOwner(sessionUser.getId())) {
            throw new RuntimeException("삭제 권한이 없습니다");
        }

//        if (!(sessionUser.getId() == board.getUser().getId())) {
//            throw new RuntimeException("삭제 권한이 없습니다");
//        }
        br.deleteById(id);
        return "redirect:/";
    }
    /* 삭제의 흐름
    1.세션에서 회원 정보 불러오기
    2.인증검사
    3.게시글 존재 확인
    4.권한체크
    5.삭제
    6.리다이렉트
     */

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
     * @param id 게시글 PK
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
    }
}//BoardController
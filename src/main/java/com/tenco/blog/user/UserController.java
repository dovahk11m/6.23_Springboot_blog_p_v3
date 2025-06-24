package com.tenco.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor //DI
@Controller
public class UserController {

    private final UserRepository ur;

    //HttpSession 이 인터페이스를 통해 세션메모리에 접근
    private final HttpSession httpSession;


    /**
     * 회원가입 화면 요청
     *
     * @return join-form.mustache
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    //회원가입 액션 처리
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO, HttpServletRequest request) {

        System.out.println("회원가입 요청 발생");
        System.out.println("회원명: " + joinDTO.getUsername());
        System.out.println("회원메일: " + joinDTO.getEmail());

        try {
            //1.데이터 검증
            joinDTO.validate();

            //2.회원명 중복검사
            User existUser = ur.findByUsername(joinDTO.getUsername());
            if (existUser != null) {
                throw new IllegalArgumentException("이미 존재하는 회원명: " + joinDTO.getUsername());
            }
            //3.DTO를 User Object로 변환
            User user = joinDTO.toEntity();

            //4.User Object를 영속화
            ur.save(user);

            return "redirect:/login-form";

        } catch (Exception e) {
            //검증 실패시 로그인창으로 복귀
            request.setAttribute("errorMessage", "잘못된 요청입니다");
            return "user/join-form";
        }
    }//join


    /**
     * 로그인 화면 요청
     *
     * @return login-form.mustache
     */
    @GetMapping("/login-form")
    public String loginForm() {
        // 반환값이 뷰(파일)이름이 됨(뷰리졸버가 실제파일 경로를 찾아감)
        return "user/login-form";
    }

    /* 🤔로그인 요청은 왜 POST로 할까?
    자원의 요청은 보통 GET 방식으로 한다
    로그인은 보안상의 이유로 다르게 하는데
    GET 방식으로 하면 히스토리에 남기 때문이다
     */
    @PostMapping("/login")
    public String login(UserRequest.LoginDTO loginDTO) {
        System.out.println("로그인 시도 발생");
        System.out.println("회원명: " + loginDTO.getUsername());
        try {
            loginDTO.validate();
            User user = ur.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
            if (user == null) {
                throw new IllegalArgumentException("잘못된 입력"); //로그인실패
            }
            httpSession.setAttribute("sessionUser", user); //세션기반인증
            //로그인성공, 리스트페이지 이동
            return "redirect:/";

        } catch (Exception e) {
            return "user/login-form";
        }
    }//login
    /* 로그인 액션 처리
    1.입력데이터 검증
    2.회원명과 비밀번호 조회와 검증
    3.로그인 성공/실패 처리
    4.성공시 서버측 메모리에 로그인정보 저장
    5.메인으로 리다이렉트
     */

    //로갓 요청
    @GetMapping("/logout")
    public String logout() {
        httpSession.invalidate();
        return "redirect:/";
    }


    // update 화면 요청
    @GetMapping("/user/update-form")
    public String updateForm() {

        return "user/update-form";
    }

}

package com.tenco.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor //DI
@Controller
public class UserController {

    private final UserRepository ur;

    /**
     * 회원가입 화면 요청
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
        System.out.println("회원명: "+joinDTO.getUsername());
        System.out.println("회원메일: "+joinDTO.getEmail());

        try {
            //1.데이터 검증
            joinDTO.validate();

            //2.회원명 중복검사
            User existUser = ur.findByUsername(joinDTO.getUsername());
            if (existUser != null) {
                throw new IllegalArgumentException("이미 존재하는 회원명: "+joinDTO.getUsername());
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


    // Login 화면 요청
    @GetMapping("/login-form")
    public String loginForm() {
        // 반환값이 뷰(파일)이름이 됨(뷰리졸버가 실제파일 경로를 찾아감)
        return "user/login-form";
    }

    // update 화면 요청

    @GetMapping("/user/update-form")
    public String updateForm() {
        return "user/update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        // "redirect : " 스프링에서 접두사를 사용하면 다른 URL 로 리다이렉트됨
        // 즉 리다이렉트 한다는것은 뷰를 렌더링하지않고 브라우저가 재요청
        return "redirect:/";
    }

}

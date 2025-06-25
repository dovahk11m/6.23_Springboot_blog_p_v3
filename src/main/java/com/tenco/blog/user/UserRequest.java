package com.tenco.blog.user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

public class UserRequest {

    //회원정보 수정용 DTO
    @Data
    @Transactional
    public static class UpdateDTO {
        private String password;
        private String email;

        public void validate() {
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            if (password.length() < 4) {
                throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다");
            }
            //간단한 이메일 형식 검증 (정규화 표현식)
            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }

        }
    }


    //여기에 DTO를 내부클래스로 설계
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;

        //여기에 JoinDTO를 User Object로 변환하는 메서드를 설계
        //🤔계층간 데이터 변환을 위해 명확하게 분리한 것이다.
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .email(this.email)
                    .build();
        }
        //여기에 회원가입시 유효성 검사 메서드도 넣었다
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("회원명은 필수입니다");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
            //간단한 이메일 형식 검증 (정규화 표현식)
            if (!email.contains("@")) {
                throw new IllegalArgumentException("잘못된 이메일 형식입니다");
            }
        }
    }//JoinDTO

    //로그인용 DTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO {
        private String username;
        private String password;

        //유효성 검사
        public void validate() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("회원명은 필수입니다");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("비밀번호는 필수입니다");
            }
        }
    }//LoginDTO

}//UserRequest

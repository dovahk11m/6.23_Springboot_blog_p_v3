package com.tenco.blog.board;

/* 😎 클라이언트로부터 넘어온 데이터를
Object 로 변환해 전달하는 DTO 역할을 담당한다. */

import com.tenco.blog.user.User;
import lombok.Data;

public class BoardRequest {

    //게시글 저장 DTO
    @Data
    public static class saveDTO {
        private String title;
        private String content;
        //username은 세션에서 가져온다

        //(User) toEntity() 호출할때 세션에서 가져온다
        public Board toEntity(User user) {
            return Board.builder()
                    .title(this.title)
                    .content(this.content)
                    .user(user)
                    .build();
        }

        public void validate() {
            if (this.title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다");
            }
            if (this.content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다");
            }
        }
    }
}

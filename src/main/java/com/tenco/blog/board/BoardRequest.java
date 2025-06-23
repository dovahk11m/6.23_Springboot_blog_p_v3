package com.tenco.blog.board;

/* 😎 클라이언트로부터 넘어온 데이터를
Object 로 변환해 전달하는 DTO 역할을 담당한다. */

import lombok.Data;

public class BoardRequest {

    //게시글 수정용 DTO 추가
    @Data
    public static class UpdateDTO {

        //밸류 오브젝트 VO
        private String title;
        private String content;
        private String username;

        //검증메서드
        public void validate() throws IllegalAccessException {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalAccessException("제목은 필수입니다");
            }
            if (content == null || content.trim().isEmpty()) {
                throw new IllegalAccessException("내용은 필수입니다");
            }
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalAccessException("이름은 필수입니다");
            }
        }
    }





    // 정적 내부 클래스로 기능별 DTO를 관리한다
    //BoardRequest.saveDTO..
    //게시글 저장 요청 데이터
    @Data
    public static class SaveDTO {
        private String title;
        private String content;
        private String username;

       /* DTO에서 Entity로 변환하는 메서드를 만든다
       이는 계층간 데이터 변환을 명확하게 분리하기 위해서다 */

        public Board toEntity() {
            return new Board(title, content, username);
        }

    }//

}

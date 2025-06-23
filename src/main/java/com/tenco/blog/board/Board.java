package com.tenco.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import utils.MyDateUtil;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Table(name = "board_tb") //테이블 명 지정
@Entity //JPA 가 이 클래스를 엔티티로 인식
public class Board {

    // @Id 이 필드가 기본키(PK)
    @Id // Identity 전략 : DB 기본 전략을 사용한다 -> Auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //칼럼명
    private String title;
    private String content;
    private String username;

    /* @CreationTimestamp: 하이버네이트 어노테이션이다
    엔티티가 처음 저장될 때 현재 시간을 자동으로 설정해준다
    pc -> db 날짜주입
    v1에서는 SQL now()를 직접 사용했지만
    v2에서는 JPA가 자동 처리한다.
     */
    @CreationTimestamp
    private Timestamp createdAt; // created_at(auto-convert to SnakeCase)

    //생성자
    public Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;

        //id와 CreatedAt은 JPA Hibernate가 자동으로 생성해준다.
    }

    //머스테치에서 표현할 시간 포맷기능을 스스로 만들자
    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }

}
// DB first > Code first
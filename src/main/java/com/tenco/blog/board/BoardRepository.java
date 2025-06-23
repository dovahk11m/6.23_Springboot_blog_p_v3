package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor //생성자 자동생성 + 멤버변수 DI 처리
@Repository //IoC / 싱글톤 / 스프링컨테이너
public class BoardRepository {

    private final EntityManager em;


    /**
     * 게시글 1건 조회
     * @param id : Board 엔티티 id 값
     * @return : Board 엔티티
     */
    public Board findById(Long id) {

        //PK조회는 무조건 em이 이득
        Board board = em.find(Board.class, id);
        return board;
    }//findById

    /**
     * 게시글 전체 조회
     * @return : Board 배열
     */
    public List<Board> findByAll() {

        //JPQL로
        String jpql = "SELECT b FROM Board b ORDER BY b.id DESC";
        TypedQuery query = em.createQuery(jpql, Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }//findByAll
}//

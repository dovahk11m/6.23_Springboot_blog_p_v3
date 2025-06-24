package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //생성자 자동생성 + 멤버변수 DI 처리
@Repository //IoC / 싱글톤 / 스프링컨테이너
public class BoardRepository {

    private final EntityManager em;






    /**
     * 게시글 저장: User 와 연관관계를 가진 Board 엔티티 영속화
     * @param board
     * @return
     */
    @Transactional
    public Board save(Board board) {
        em.persist(board);
        return board;
    }
    /* 비영속 상태의 Board 오브젝트를 영속성 컨텍스트에 저장하면
    이후에는 같은 메모리주소를 가리키게 된다
    엔티티매니저는 워낙 안정적이라 단위테스트를 안해도 된다
     */

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

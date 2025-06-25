package com.tenco.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor //생성자 자동생성 + 멤버변수 DI 처리
@Repository //IoC / 싱글톤 / 스프링컨테이너
public class BoardRepository {

    private final EntityManager em;

    //수정 요청 feat.더티체킹
    @Transactional
    public Board updateById(Long id, BoardRequest.UpdateDTO reqDTO) {
        //1.수정할 게시글을 영속상태로 조회
        Board board = findById(id);
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
        return board;
    }//updateById
    /* 더티체킹 흐름
    1.영속성 컨텍스트가 엔티티 최초 조회 상태를 스냅샷으로 보관
    2.필드값 변경시 현재상태와 스냅샷 비교
    3.트랜잭션 커밋시점에 변경된 필드만 UPDATE 쿼리 자동 시전
     */


    //게시글 삭제 - JPQL
    @Transactional
    public void deleteById(Long id) {
        String jpql = "DELETE FROM Board b WHERE b.id = :id ";
        Query query = em.createQuery(jpql);
        query.setParameter("id", id);

        int deletedCount = query.executeUpdate();
        if (deletedCount == 0) {
            throw new IllegalArgumentException("삭제할 게시글이 없습니다");
        }
    }

    //게시글 삭제 - 영속성 컨텍스트
    @Transactional
    public void deleteByIdSafely(Long id) {
        Board board = em.find(Board.class, id); //찾기 & 영속화
        if (board == null) {
            throw new IllegalArgumentException("삭제할 게시글이 없습니다");
        }

        em.remove(board);
    }


    /**
     * 게시글 저장: User 와 연관관계를 가진 Board 엔티티 영속화
     *
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
     *
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
     *
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

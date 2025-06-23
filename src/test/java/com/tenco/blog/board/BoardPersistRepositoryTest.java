package com.tenco.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {

    @Autowired
    private BoardPersistRepository br;

    @Test
    public void deleteById_test() {
        //given
        Long id = 1L;

        //when - 삭제할 게시글 존재여부 확인
        Board targetBoard = br.findById(id);
        Assertions.assertThat(targetBoard).isNotNull();

        br.deleteById(id);

        //then - 삭제후 게시물 숫자 확인
        List<Board> afterDelete = br.findAll();
        Assertions.assertThat(afterDelete.size()).isEqualTo(3);
    }


    @Test
    public void findAll_test() {
        //given

        //when
        List<Board> boardList = br.findAll();

        //then
        System.out.println("사이즈:" + boardList.size());
        System.out.println("첫 제목:" + boardList.get(0).getTitle());

        for (Board board : boardList) {
            Assertions.assertThat(board.getId()).isNotNull();
        }

    }
    /* 네이티브 쿼리를 사용한다는 것은 영속성 컨텍스트를 우회하는 것이다
    JPQL도 영속성 컨텍스트를 우회하지만
    조회된 이후에는 영속성 상태가 된다

     */







    @Test
    public void save_test() {
        //given
        Board board = new Board("샘플제목","샘플내용","샘플이름");

        //저장 전 객체의 상태값 확인
        Assertions.assertThat(board.getId()).isNull();
        System.out.println("DB 저장 전 board:"+board);

        //when
        Board savedBoard = br.save(board); //영속성 컨텍스트를 통한 엔티티 저장

        //then

        //1.저장 후 자동생성된 ID 확인
        Assertions.assertThat(savedBoard.getId()).isNotNull();
        Assertions.assertThat(savedBoard.getId()).isGreaterThan(0);

        //2.내용 일치 여부 확인
        Assertions.assertThat(savedBoard.getTitle()).isEqualTo("샘플제목");

        //3.JPA가 자동 생성된 시간 확인
        Assertions.assertThat(savedBoard.getCreatedAt()).isNotNull();

        //4.원본 객체 board, 영속성 컨텍스트에 저장한 savedBoard
        Assertions.assertThat(board).isSameAs(savedBoard);
    }

}//

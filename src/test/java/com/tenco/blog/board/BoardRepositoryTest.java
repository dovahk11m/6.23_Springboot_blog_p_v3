package com.tenco.blog.board;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository br;

    @Test
    public void deleteById_JPQL삭제테스트() {
        //given
        Long deleteId = 1L;
        //when
        br.deleteById(deleteId);
        //then
        List<Board> boardList = br.findByAll();
        Assertions.assertThat(boardList.size()).isEqualTo(9);
    }


}

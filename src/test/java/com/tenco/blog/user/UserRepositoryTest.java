package com.tenco.blog.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository ur;

    @Test
    public void findByUsername_존재하지않는사용자테스트() {
        //given
        String username = "nonUser";
        //when
        User user = ur.findByUsername(username);
        //then
        Assertions.assertThat(user).isNull();
    }

    @Test
    public void findByUsername_사용자조회테스트() {
        //given
        String username = "admin";
        //when
        User user = ur.findByUsername(username);
        //then
        Assertions.assertThat(user).isNotNull();
    }


    @Test
    public void save_회원가입테스트() {
        //given
        User user = User.builder()
                .username("testUser")
                .email("a@naver.com")
                .password("asd1234")
                .build();
        //when
        User savedUser = ur.save(user);
        //then
        //id할당 여부 확인
        Assertions.assertThat(savedUser.getId()).isNotNull();
        //데이터 등록 확인
        Assertions.assertThat(savedUser.getUsername()).isEqualTo("testUser");
        //원본 user Object와 영속화된 Object가 동일한 객체인지 확인
        //영속성 컨텍스트틑 같은 엔티티에 대해 같은 인스턴스를 보장
        Assertions.assertThat(user).isSameAs(savedUser);

    }//회원가입테스트

}

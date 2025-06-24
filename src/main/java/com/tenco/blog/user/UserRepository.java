package com.tenco.blog.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    /**
     * 회원정보 저장 처리
     * @param user (비영속 상태)
     * @return user (엔티티 반환)
     */
    @Transactional
    public User save(User user) {
        em.persist(user);
        return user;
    }
    /*매개변수에 들어오는 user Object는 아직 비영속화된 상태이다.
    persist 하면 그때부터 영속성 컨텍스트로 관리된다.
    Transaction Commit이 끝나면 INSERT 쿼리를 실행한다.
     */

    //사용자명 중복체크 조회
    public User findByUsername(String username) {
        //String jpql = "SELECT u FROM User u WHERE u.username = :username ";
        //TypedQuery<User> typedQuery = em.createQuery(jpql, User.class);
        //typedQuery.setParameter("username", username);
        //return typedQuery.getSingleResult();

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username ";
            return em.createQuery(jpql, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}//class

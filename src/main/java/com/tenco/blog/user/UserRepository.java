package com.tenco.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;


    /**
     * 로그인 요청 (회원 정보 조회)
     * @param username
     * @param password
     * @return 성공시 User 엔티티, 실패시 null 반환
     */
    public User findByUsernameAndPassword(String username, String password) {

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password ";
            TypedQuery<User> typedQuery = em.createQuery(jpql, User.class);
            typedQuery.setParameter("username", username);
            typedQuery.setParameter("password", password);
            return typedQuery.getSingleResult();
        } catch (Exception e) {
            return null; //로그인 실패
        }
    }//findByUsernameAndPassword


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

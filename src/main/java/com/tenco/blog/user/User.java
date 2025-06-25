package com.tenco.blog.user;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Table(name = "user_tb")
@Entity
@NoArgsConstructor
public class User {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //유니크키
    private String username;

    private String password;
    private String email;

    @CreationTimestamp //엔티티가 영속화될때 자동으로 현재시간 설정
    private Timestamp createdAt;

    @Builder //객체 생성시 가독성과 안정성 향상
    public User(Long id, String username, String password, String email, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
}//User

package com.tenco.blog.board;
import com.tenco.blog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import utils.MyDateUtil;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "board_tb")
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    //V3에서 Board 엔티티는 User 엔티티와 연관관계로 설계한다
    //이렇게 만들면 board.user.getId(); 이런 식의 코드를 쓸 수 있다
    @ManyToOne(fetch = FetchType.LAZY) //다대일..
    @JoinColumn(name = "user_id") //외래키
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;

    public String getTime() {
        return MyDateUtil.timestampFormat(createdAt);
    }

    //게시글의 소유자를 직접 확인하는 기능을 만들자
    public boolean isOwner(Long checkUserId) {
        return this.user.getId().equals(checkUserId);
    }


}//Board

package com.tenco.blog.user;

public class userBuilderTest {

    //메인테스트
    public static void main(String[] args) {

        Student student1 = Student.builder()
                .id(100)
                .username("고길동")
                .build(); //new Student
        //롬복 빌더패턴을 사용한다면 막줄에 .build() 반드시 호출

        User user1 = User
                .builder()
                .id(1).username("홍길동")
                .password("1234")
                .email("a@naver.com");
        System.out.println(user1);

        User user2 = User
                .builder().id(2)
                .username("티모");
        System.out.println(user2);
    }
}

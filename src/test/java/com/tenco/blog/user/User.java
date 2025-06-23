package com.tenco.blog.user;

// 6.23 빌더패턴을 사용하여 User 클래스를 설계한다

public class User {

    private Integer id;
    private String username;
    private String password;
    private String email;

    //빌더패턴으로 설계

    //1.static메서드를 활용해 객체생성 메서드를 만든다
    public static User builder() {
        return new User();
    }

    //2.각각의 멤버메서드를 세팅하는 메서드를 만든다
    public User id(Integer id) {
        this.id = id;
        return this;
    }

    public User username(String username) {
        this.username = username;
        return this;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

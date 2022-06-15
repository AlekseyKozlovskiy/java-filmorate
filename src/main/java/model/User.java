package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private long id;
    private Set<Long> friends = new HashSet<>();

    private String email;
    private String login;

    private String name;


    private Set<User> usersToApprove = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", friends=" + friends +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", usersToApprove=" + usersToApprove +
                ", birthday=" + birthday +
                '}';
    }

    public void addRequestFriend(User user) {
        usersToApprove.add(user);
    }

}
package model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
//@RequiredArgsConstructor
public class User {

    private long id;
    private Set<Long> friends = new HashSet<>();


    private String email;
    //    @NotBlank
    private String login;
    private String name;


    private Set<User> usersToApprove = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.id = NumberGenerator.getUserId();
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public void addRequestFriend(User user){
     usersToApprove.add(user);
    }

    public void addToFriendsList(User user){
        usersToApprove.remove(user);
        friends.add(user.getId());
    }
}
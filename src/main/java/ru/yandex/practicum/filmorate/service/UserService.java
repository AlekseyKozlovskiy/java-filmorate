package ru.yandex.practicum.filmorate.service;

import model.User;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User addFriend(Long id, Long friendId) {

        inMemoryUserStorage.getUserMap().get(id).getFriends().add(friendId);
        return inMemoryUserStorage.getUserMap().get(id);
    }

    public User deleteFriend(Long id, Long friendId) {
        inMemoryUserStorage.getUserMap().get(id).getFriends().remove(friendId);

        return inMemoryUserStorage.getUserMap().get(id);
    }

    public Set<User> getAllFriends(Long id) {
        Set<Long> friends = inMemoryUserStorage.getUserMap().get(id).getFriends();
        Set<User> userSet = new HashSet<>();
        friends.forEach(f -> userSet.add(inMemoryUserStorage.getUserMap().get(f)));
        return userSet;
    }

    public Set<Long> getMutualFriends(Long id, Long otherId) {
        Set<Long> use = new HashSet<>(inMemoryUserStorage.getUserMap().get(id).getFriends());
        use.retainAll(inMemoryUserStorage.getUserMap().get(otherId).getFriends());
        return use;
    }

}

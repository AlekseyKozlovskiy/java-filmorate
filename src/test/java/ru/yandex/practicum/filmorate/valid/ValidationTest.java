package ru.yandex.practicum.filmorate.valid;

import lombok.SneakyThrows;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.FilmorateApplicationTests;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.ValidationException;
import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class ValidationTest extends FilmorateApplicationTests {

    @Autowired
    private InMemoryUserStorage inMemoryUserStorage;

    @Test
    @SneakyThrows
    public void testValidationUser() {
        User sergey = objectMapper.readValue(new File("src/test/resources/json/User.json"), User.class);
        sergey.setEmail("not valid email");

        assertThrows(ValidationException.class,
                () -> inMemoryUserStorage.create(sergey));
        sergey.setEmail("valid@email.ru");

        sergey.setLogin("not valid login");
        assertThrows(ValidationException.class,
                () -> inMemoryUserStorage.create(sergey));

        sergey.setLogin("serdj");

        sergey.setBirthday(LocalDate.now().plusDays(1L));
        assertThrows(ValidationException.class,
                () -> inMemoryUserStorage.create(sergey));

    }
}

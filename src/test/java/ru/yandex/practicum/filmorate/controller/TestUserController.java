package ru.yandex.practicum.filmorate.controller;

import lombok.SneakyThrows;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.FilmorateApplicationTests;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TestUserController extends FilmorateApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryUserStorage inMemoryUserStorage;

    @Test
    @SneakyThrows
    public void testPostUser() {
        User sergey = objectMapper.readValue(new File("src/test/resources/json/User.json"), User.class);

        mockMvc
                .perform(post("/users")
                        .content(objectMapper.writeValueAsString(sergey))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, inMemoryUserStorage.getUserMap().size());
        assertEquals("Sergey", inMemoryUserStorage.getUserMap().get(1L).getName());

    }
    @Test
    @SneakyThrows
    public void testPutUser() {
        User sergey = objectMapper.readValue(new File("src/test/resources/json/User.json"), User.class);
        User sergeyNew = objectMapper.readValue(new File("src/test/resources/json/ChangedUser.json"), User.class);

        mockMvc
                .perform(put("/users", sergey)
                        .content(objectMapper.writeValueAsString(sergeyNew))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertEquals(1, inMemoryUserStorage.getUserMap().size());
        assertEquals("new@email.com", inMemoryUserStorage.getUserMap().get(1L).getEmail());

    }
}

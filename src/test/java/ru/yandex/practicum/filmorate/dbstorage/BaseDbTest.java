package ru.yandex.practicum.filmorate.dbstorage;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.FilmorateApplicationTests;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Sql(scripts = "population.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BaseDbTest extends FilmorateApplicationTests {
}

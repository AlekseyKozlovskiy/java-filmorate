package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmorateApplicationTests {

	private HttpClient client = HttpClient.newHttpClient();

	@Test
	void contextLoads() {
	}
//	@Test
//	void postUserTest() throws IOException, InterruptedException {
//		URI uri = URI.create("http://localhost:8080/users");
//		String j = "{\"login\":\"dolore\",\"name\":\"est adipisicing\"," +
//				"\"email\":\"mail@mail.ru\",\"birthday\":\"1895-12-27\"}";
//
//		var request = HttpRequest.newBuilder()
//				.uri(uri)
//				.header("Content-Type", "application/json")
//				.POST(HttpRequest.BodyPublishers.ofString(j))
//				.build();
//
//		HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
//		HttpResponse<String> response = client.send(request, handler);
//
//		assertEquals(200, response.statusCode());
//	}
//	@Test
//	void postUserBirthdayTest() throws IOException, InterruptedException {
//		URI uri = URI.create("http://localhost:8080/users");
//		String j = "{\"login\":\"dolore\",\"name\":\"est adipisicing\"," +
//				"\"email\":\"mail@mail.ru\",\"birthday\":\"2022-04-20\"}";
//
//		var request = HttpRequest.newBuilder()
//				.uri(uri)
//				.header("Content-Type", "application/json")
//				.POST(HttpRequest.BodyPublishers.ofString(j))
//				.build();
//
//		HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
//		HttpResponse<String> response = client.send(request, handler);
//
//		assertEquals(500, response.statusCode());
//	}

}

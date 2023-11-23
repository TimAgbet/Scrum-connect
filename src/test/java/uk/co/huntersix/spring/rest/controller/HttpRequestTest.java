package uk.co.huntersix.spring.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getPerson_whenExists_returnsPerson() {
        String response = restTemplate.getForObject("http://localhost:" + port + "/person/smith/mary", String.class);
        assertThat(response).contains("Mary");
    }

    @Test
    public void getPersonsByLastName_whenExists_returnsPeople() {
        String response = restTemplate.getForObject("http://localhost:" + port + "/person/archer", String.class);
        assertThat(response).contains("Brian");
    }

    @Test
    public void getPersonsByLastName_whenNoneFound_returnsNotFound() {
        String response = restTemplate.getForObject("http://localhost:" + port + "/person/empty", String.class);
        assertThat(response).contains("Not Found");
    }
}
package stylepatrick.binance.api.consumer;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "user = user",
            "password = password"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationTests {

    /* Example for TestRestTemplate -> Should be used for tests which are not related to the Server itself

    @Value("${user}")
    private String user;

    @Value("${password}")
    private String password;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testApiSecured() {
        ResponseEntity<String> result = template.getForEntity("/api/balance/test", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

    @Test
    public void testApiTest() {
        ResponseEntity<String> result = template.withBasicAuth(user, password)
                .getForEntity("/api/balance/test", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testApiStatsCoin() {
        ResponseEntity<Object> result = template.withBasicAuth(user, password)
                .getForEntity("/api/balance/stats/coin", Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testApiStatsFull() {
        ResponseEntity<Object> result = template.withBasicAuth(user, password)
                .getForEntity("/api/balance/stats/full", Object.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    */

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mockMvcTestApiSecured() throws Exception {
        this.mockMvc.perform(get("/api/balance/test")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "${user}", password = "${password}")
    public void mockMvcTestApiTest() throws Exception {
        this.mockMvc.perform(get("/api/balance/test")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Running!")));
    }

    @Test
    @WithMockUser(username = "${user}", password = "${password}")
    public void mockMvcTestApiCoinStats() throws Exception {
        this.mockMvc.perform(get("/api/balance/stats/full")).andExpect(status().isOk());
    }

}

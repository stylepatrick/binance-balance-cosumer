package stylepatrick.binance.api.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import stylepatrick.binance.api.consumer.repository.BinanceBalanceRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        properties = {
                "user = user",
                "password = password"
        })
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BinanceBalanceRepository binanceBalanceRepository;

    @Test
    public void apiUnauthorized() throws Exception {
        this.mockMvc.perform(get("/api/balance/test")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "${user}", password = "${password}")
    public void apiAuthorized() throws Exception {
        this.mockMvc.perform(get("/api/balance/test")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Running!")));
    }

    @Test
    @WithMockUser(username = "${user}", password = "${password}")
    public void fullStats() throws Exception {
        this.mockMvc.perform(get("/api/balance/stats/full")).andExpect(status().isOk());
        assertFalse(binanceBalanceRepository.findAll().isEmpty());
    }

}

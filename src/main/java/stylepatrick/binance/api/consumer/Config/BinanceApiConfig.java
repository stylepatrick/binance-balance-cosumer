package stylepatrick.binance.api.consumer.Config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BinanceApiConfig {

    private final AppConfig appConfig;

    @Bean
    public BinanceApiRestClient createBinanceApiRestClient() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(appConfig.getApiKey(), appConfig.getSecret());
        return factory.newRestClient();
    }

}

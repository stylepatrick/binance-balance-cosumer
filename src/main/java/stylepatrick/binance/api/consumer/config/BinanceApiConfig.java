package stylepatrick.binance.api.consumer.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BinanceApiConfig {

    private final BinanceConfig binanceConfig;

    @Bean
    public BinanceApiRestClient createBinanceApiRestClient() {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binanceConfig.getApiKey(), binanceConfig.getSecret());
        return factory.newRestClient();
    }

}

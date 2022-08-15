package stylepatrick.binance.api.consumer.config;

import com.binance.connector.client.impl.SpotClientImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BinanceApiConfig {

    private final BinanceConfig binanceConfig;

    @Bean
    public SpotClientImpl getSpotClientImpl() {
        return new SpotClientImpl(binanceConfig.getApiKey(), binanceConfig.getSecret());
    }
}

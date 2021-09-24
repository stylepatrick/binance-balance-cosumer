package stylepatrick.binance.api.consumer.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "binance")
@Getter
@Setter
public class BinanceConfig {

    private String apiKey;
    private String secret;
}

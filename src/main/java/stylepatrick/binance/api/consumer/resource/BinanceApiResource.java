package stylepatrick.binance.api.consumer.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stylepatrick.binance.api.consumer.model.TotalAssetStatsDto;
import stylepatrick.binance.api.consumer.notification.TelegramNotification;
import stylepatrick.binance.api.consumer.service.BinanceApiService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/balance")
public class BinanceApiResource {

    private final BinanceApiService binanceApiService;
    private final TelegramNotification telegramNotification;

    @GetMapping(value = "test")
    public ResponseEntity<String> testApi() {
        return ResponseEntity.ok("Running!");
    }

    @GetMapping(value = "stats/full")
    public ResponseEntity<TotalAssetStatsDto> getFullStats() {
        return ResponseEntity.ok(binanceApiService.getTotalAssetStats());
    }

    @GetMapping(value = "stats/notification")
    public ResponseEntity<Object> sendNotification() {
        this.telegramNotification.sendBalanceToTelegram();
        return ResponseEntity.noContent().build();
    }
}

package stylepatrick.binance.api.consumer.resource;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stylepatrick.binance.api.consumer.model.CoinStats;
import stylepatrick.binance.api.consumer.model.FullStats;
import stylepatrick.binance.api.consumer.service.BinanceApiService;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/balance")
public class BinanceApiResource {

    private final BinanceApiService binanceApiService;

    @GetMapping(value = "stats/coin")
    public ResponseEntity<Map<String, CoinStats>> getStatsPerCoin() {
        return ResponseEntity.ok(binanceApiService.getStatsPerCoin());
    }

    @GetMapping(value = "stats/full")
    public ResponseEntity<FullStats> getFullStats() {
        return ResponseEntity.ok(binanceApiService.getFullStats());
    }
}

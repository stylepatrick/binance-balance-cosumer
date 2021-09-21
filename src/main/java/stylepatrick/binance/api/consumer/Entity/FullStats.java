package stylepatrick.binance.api.consumer.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter(AccessLevel.PROTECTED)
public class FullStats {

    public FullStats(Double amount, Map<String, CoinStats> coins) {
        this.amount = amount;
        this.coins = coins;
    }

    private Double amount;
    private Map<String, CoinStats> coins;

}

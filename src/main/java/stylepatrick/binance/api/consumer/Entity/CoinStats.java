package stylepatrick.binance.api.consumer.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PROTECTED)
public class CoinStats {

    public CoinStats(Double amount, Double priceInUsdt) {
        this.amount = amount;
        this.priceInUsdt = priceInUsdt;
        this.calculateSumInUsdt();
    }

    @Setter
    private Double amount;

    private Double priceInUsdt;
    private Double sumInUsdt;

    private Double calculateSumInUsdt() {
        return this.sumInUsdt = this.amount * this.priceInUsdt;
    }
}

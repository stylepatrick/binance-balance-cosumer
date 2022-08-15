package stylepatrick.binance.api.consumer.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public abstract class Asset {

    private String asset;
    private String amount;
    private BigDecimal amountInUsdt = BigDecimal.ZERO;

    public static void sort(List<? extends Asset> positions) {
        positions.sort(Comparator.comparing(Asset::getAmountInUsdt).reversed());

    }

    public String getAsset() {
        return this.asset;
    }

    public String getAmount() {
        return this.amount;
    }

    public BigDecimal getAmountInUsdt() {
        return this.amountInUsdt;
    }

    public void setAmountInUsdt(BigDecimal amount) {
        this.amountInUsdt = amount;
    }

    public abstract AssetType getAssetType();


}

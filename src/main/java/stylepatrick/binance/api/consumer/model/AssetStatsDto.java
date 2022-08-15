package stylepatrick.binance.api.consumer.model;

import java.math.BigDecimal;
import java.util.List;

public record AssetStatsDto(BigDecimal totAmountInUsdt, AssetType assetType, List<? extends Asset> assetList) {
}

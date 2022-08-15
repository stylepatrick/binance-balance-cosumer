package stylepatrick.binance.api.consumer.model;

import java.math.BigDecimal;
import java.util.List;

public record TotalAssetStatsDto(BigDecimal totAmountInUsdt, List<AssetStatsDto> assetStatsDtoList) {
}

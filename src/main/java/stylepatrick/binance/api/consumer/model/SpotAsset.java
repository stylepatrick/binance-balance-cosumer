package stylepatrick.binance.api.consumer.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
public final class SpotAsset extends Asset {

    private String free;
    private String locked;
    private String freeze;
    private String withdrawing;
    private String ipoable;
    private String btcValuation;

    @Override
    public String getAmount() {
        return free;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.SPOT;
    }


}

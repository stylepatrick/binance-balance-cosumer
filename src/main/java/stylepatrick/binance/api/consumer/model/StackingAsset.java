package stylepatrick.binance.api.consumer.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
public final class StackingAsset extends Asset {

    private int positionId;
    private String productId;
    private long purchaseTime;
    private int duration;
    private int accrualDays;
    private String rewardAsset;
    private String rewardAmt;
    private String nextInterestPay;
    private int payInterestPeriod;
    private String redeemAmountEarly;
    private long interestEndDate;
    private long deliverDate;
    private int redeemPeriod;
    private String redeemingAmt;
    private boolean canRedeemEarly;
    private boolean renewable;
    private String type;
    private String status;
    private String apy;

    @Override
    public AssetType getAssetType() {
        return AssetType.STACKING;
    }
}

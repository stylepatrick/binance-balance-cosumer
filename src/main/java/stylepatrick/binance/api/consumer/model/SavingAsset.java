package stylepatrick.binance.api.consumer.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
public final class SavingAsset extends Asset {

    private String productId;
    private String productName;
    private String dailyInterestRate;
    private String annualInterestRate;
    private String avgAnnualInterestRate;
    private String totalAmount;
    private String lockedAmount;
    private String freeAmount;
    private String freezeAmount;
    private String totalInterest;
    private boolean canRedeem;
    private String redeemingAmount;
    private String todayPurchasedAmount;
    private Object tierAnnualInterestRate;

    @Override
    public String getAmount() {
        return totalAmount;
    }

    @Override
    public AssetType getAssetType() {
        return AssetType.SAVING;
    }

}

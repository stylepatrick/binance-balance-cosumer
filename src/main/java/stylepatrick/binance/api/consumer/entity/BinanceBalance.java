package stylepatrick.binance.api.consumer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import stylepatrick.binance.api.consumer.model.AssetType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Table(name = "binance_balance")
@NoArgsConstructor
@SequenceGenerator(
        name = "seq_binance_balance_id",
        sequenceName = "SEQ_BINANCE_BALANCE_ID",
        allocationSize = 1)
public class BinanceBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_binance_balance_id")
    private Long id;

    private String asset;
    private BigDecimal amount;
    private BigDecimal amountInUsdt;

    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    private Date crdt;

    public BinanceBalance(String asset, BigDecimal amount, BigDecimal amountInUsdt, AssetType assetType, Date crdt) {
        this.asset = asset;
        this.amount = amount;
        this.amountInUsdt = amountInUsdt;
        this.assetType = assetType;
        this.crdt = crdt;
    }
}

package stylepatrick.binance.api.consumer.service;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.market.TickerPrice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stylepatrick.binance.api.consumer.model.CoinStats;
import stylepatrick.binance.api.consumer.model.FullStats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class BinanceApiService {

    private final BinanceApiRestClient binanceApiRestClient;

    public Map<String, CoinStats> getStatsPerCoin() {
        Account account = binanceApiRestClient.getAccount();
        return buildCoinStatsHashMap(account);
    }

    public FullStats getFullStats() {
        ArrayList<Double> sumPerCoinsInUsdt = new ArrayList<>();
        Account account = binanceApiRestClient.getAccount();
        Map<String, CoinStats> coinStatsHashMap = buildCoinStatsHashMap(account);
        coinStatsHashMap.forEach((s, coinStats) -> sumPerCoinsInUsdt.add(coinStats.getSumInUsdt()));

        double sum = 0.0;
        for (Double i : sumPerCoinsInUsdt)
            sum = sum + i;

        return new FullStats(sum, coinStatsHashMap);
    }

    private Map<String, CoinStats> buildCoinStatsHashMap(Account account) {
        Map<String, CoinStats> coinStatsHashMap = new HashMap<>();
        account.getBalances().forEach(assetBalance -> {
            if (Double.parseDouble(assetBalance.getFree()) > 0.0) {
                String shortName = assetBalance.getAsset();
                if (shortName.startsWith("LD")) {
                    shortName = shortName.substring(2);
                }
                if (shortName.equals("BETH")) {
                    shortName = "ETH";
                }
                if (shortName.equals("NFT")) {
                    shortName = "USDT";
                }
                if (!shortName.equals("USDT")) {
                    TickerPrice price = binanceApiRestClient.getPrice(shortName + "USDT");
                    CoinStats balance = new CoinStats(Double.parseDouble(assetBalance.getFree()), Double.parseDouble(price.getPrice()));
                    if (coinStatsHashMap.containsKey(shortName)) {
                        coinStatsHashMap.get(shortName).setAmount(coinStatsHashMap.get(shortName).getAmount() + Double.parseDouble(assetBalance.getFree()));
                        coinStatsHashMap.replace(shortName, new CoinStats(coinStatsHashMap.get(shortName).getAmount(), Double.parseDouble(price.getPrice())));
                    } else {
                        coinStatsHashMap.put(shortName, balance);
                    }
                }
            }
        });
        return coinStatsHashMap;
    }
}

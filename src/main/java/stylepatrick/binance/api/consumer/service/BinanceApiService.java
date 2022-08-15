package stylepatrick.binance.api.consumer.service;


import com.binance.connector.client.impl.SpotClientImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import stylepatrick.binance.api.consumer.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class BinanceApiService {

    private final SpotClientImpl spotClientImpl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public TotalAssetStatsDto getTotalAssetStats() {
        List<SpotAsset> spotAssetList = getAllSpotAssets();
        List<StackingAsset> stackingAssetList = getAllStackingAssets();
        List<SavingAsset> savingAssetList = getAllSavingAssets();

        List<Asset> fullAssetList = new ArrayList<>();
        fullAssetList.addAll(spotAssetList);
        fullAssetList.addAll(stackingAssetList);
        fullAssetList.addAll(savingAssetList);

        HashSet<TickerSymbolPrice> tickerSymbolPriceList = getTickerSymbolPrice(fullAssetList);

        lookupTickerPrice(tickerSymbolPriceList, spotAssetList);
        lookupTickerPrice(tickerSymbolPriceList, stackingAssetList);
        lookupTickerPrice(tickerSymbolPriceList, savingAssetList);

        List<AssetStatsDto> assetStatsDtoList = new ArrayList<>();
        assetStatsDtoList.add(new AssetStatsDto(sumOfUsdtFromAssetList(spotAssetList), AssetType.SPOT, spotAssetList));
        assetStatsDtoList.add(new AssetStatsDto(sumOfUsdtFromAssetList(stackingAssetList), AssetType.STACKING, stackingAssetList));
        assetStatsDtoList.add(new AssetStatsDto(sumOfUsdtFromAssetList(savingAssetList), AssetType.SAVING, savingAssetList));

        return new TotalAssetStatsDto(sumOfUsdtFromAssetList(fullAssetList), assetStatsDtoList);
    }

    private List<SpotAsset> getAllSpotAssets() {
        List<SpotAsset> spotAssetList;

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("type", "SPOT");

        try {
            spotAssetList = objectMapper.readValue(
                    spotClientImpl.createWallet().getUserAsset(parameters),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return spotAssetList;
    }

    private List<StackingAsset> getAllStackingAssets() {
        List<StackingAsset> stackingAssetList;

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("product", "STAKING");
        try {
            stackingAssetList = objectMapper.readValue(
                    spotClientImpl.createStaking().getPosition(parameters),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return stackingAssetList;
    }

    private List<SavingAsset> getAllSavingAssets() {
        List<SavingAsset> savingAssetList;

        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        try {
            savingAssetList = objectMapper.readValue(
                    spotClientImpl.createSavings().flexibleProductPosition(parameters),
                    new TypeReference<>() {
                    }
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return savingAssetList;
    }

    private HashSet<TickerSymbolPrice> getTickerSymbolPrice(List<Asset> assetList) {
        HashSet<TickerSymbolPrice> tickerSymbolPriceLookup = new HashSet<>();

        HashSet<String> uniqueAssets = new HashSet<>();
        assetList.forEach(position -> uniqueAssets.add(position.getAsset() + "USDT"));

        uniqueAssets.forEach(ticker -> {
            LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
            parameters.put("symbol", ticker);
            try {
                tickerSymbolPriceLookup.add(objectMapper.readValue(spotClientImpl.createMarket().tickerSymbol(parameters), TickerSymbolPrice.class));
            } catch (Exception e) {
                System.out.println(ticker + " - pair doesnt exist");
            }
        });

        return tickerSymbolPriceLookup;
    }

    private void lookupTickerPrice(HashSet<TickerSymbolPrice> tickerSymbolPriceList, List<? extends Asset> assetList) {
        assetList.forEach(position -> tickerSymbolPriceList.forEach(tickerSymbolPrice -> {
            String ticker = position.getAsset();
            if (ticker.equals("BETH")) {
                ticker = "ETH";
            }
            if (tickerSymbolPrice.symbol().startsWith(ticker)) {
                position.setAmountInUsdt(new BigDecimal(position.getAmount()).multiply(new BigDecimal(tickerSymbolPrice.price())).setScale(2, RoundingMode.HALF_UP));
            }
        }));
        Asset.sort(assetList);
    }

    private BigDecimal sumOfUsdtFromAssetList(List<? extends Asset> assetList) {
        return assetList
                .stream()
                .map(Asset::getAmountInUsdt)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

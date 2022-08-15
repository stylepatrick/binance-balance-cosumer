package stylepatrick.binance.api.consumer.notification;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stylepatrick.binance.api.consumer.config.TelegramConfig;
import stylepatrick.binance.api.consumer.model.TotalAssetStatsDto;
import stylepatrick.binance.api.consumer.service.BinanceApiService;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Component
@AllArgsConstructor
public class TelegramNotification {

    private final TelegramConfig telegramConfig;
    private final BinanceApiService binanceApiService;

    @Scheduled(cron = "${telegram.scheduler}")
    public void sendBalanceToTelegram() {
        TotalAssetStatsDto totalAssetStatsDto = this.binanceApiService.getTotalAssetStats();
        StringBuilder telegramMessage = new StringBuilder();
        telegramMessage
                .append("<b>Wallet Balance: ")
                .append(totalAssetStatsDto.totAmountInUsdt())
                .append("$</b>\n\n");
        totalAssetStatsDto.assetStatsDtoList().forEach(assetStatsDto -> {
            telegramMessage
                    .append("<b><i>")
                    .append(assetStatsDto.assetType().toString())
                    .append(": ")
                    .append(assetStatsDto.totAmountInUsdt())
                    .append("$</i></b>\n");
            assetStatsDto.assetList().forEach(asset -> telegramMessage
                    .append("<b>")
                    .append(asset.getAsset())
                    .append("</b>: ")
                    .append(asset.getAmountInUsdt())
                    .append("$ (")
                    .append(asset.getAmount())
                    .append(") ")
                    .append("\n")
            );
            telegramMessage.append("\n");

        });
        sendToTelegram(telegramMessage.toString());
    }

    private void sendToTelegram(String text) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s&parse_mode=HTML";
        String apiToken = this.telegramConfig.getToken();
        String chatId = this.telegramConfig.getChatId();

        urlString = String.format(urlString, apiToken, chatId, URLEncoder.encode(text));

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

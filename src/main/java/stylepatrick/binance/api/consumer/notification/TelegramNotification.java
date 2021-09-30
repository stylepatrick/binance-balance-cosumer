package stylepatrick.binance.api.consumer.notification;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stylepatrick.binance.api.consumer.config.TelegramConfig;
import stylepatrick.binance.api.consumer.model.FullStats;
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

    @Scheduled(cron = "0 0 5,18 * * *")
    public void sendBalanceToTelegram() {
        FullStats fullStats = this.binanceApiService.getFullStats();
        StringBuilder coinsAmount = new StringBuilder();
        fullStats.getCoins().forEach((s, coinStats) -> coinsAmount.append(("<b>" + s + "</b>: " + roundToTwoDecimals(coinStats.getSumInUsdt()) + "$ (" + roundToTwoDecimals(coinStats.getAmount()) + ") " + "\n")));
        sendToTelegram("<b>Wallet Balance: " + roundToTwoDecimals(fullStats.getAmount()) + "$</b>\n\n" + coinsAmount);
    }

    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
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

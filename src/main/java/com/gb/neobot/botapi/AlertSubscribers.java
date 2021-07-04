package com.gb.neobot.botapi;

import com.gb.neobot.NeoTelegramBot;
import com.gb.neobot.model.Coins;
import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.model.SmartSubscribe;
import com.gb.neobot.service.CoinsGetInfoService;
import com.gb.neobot.service.SimpleSubscribeService;
import com.gb.neobot.service.SmartSubscribeService;
import com.gb.neobot.service.UserService;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Data
@Configuration
@EnableScheduling
public class AlertSubscribers {
    private final NeoTelegramBot telegramBot;
    private final SimpleSubscribeService simpleSubscribeService;
    private final SmartSubscribeService smartSubscribeService;
    private final CoinsGetInfoService coinsGetInfoService;
    private final UserService userService;

    @Scheduled(fixedDelay = 15000)
    public void pub() throws TelegramApiException {
        List<SimpleSubscribe> simpleSubscribeList = simpleSubscribeService.findAll();
        List<SmartSubscribe> smartSubscribes = smartSubscribeService.findAll();
        Coins coins = null;
        if(simpleSubscribeList != null || smartSubscribes != null){
            coins = getCoinsGetInfoService().getAllCoins();
        }

        if (simpleSubscribeList != null) {
            for (SimpleSubscribe ss : simpleSubscribeList) {
                telegramBot.execute(new SendMessage().setChatId(userService.findById(ss.getUserId()).getChatId()).setText(createSubscribeText(ss.getCoin(), coins)));
            }
        }
        if (smartSubscribes != null) {
            for (SmartSubscribe ss : smartSubscribes) {
                Double price = coinsGetInfoService.getCoinByName(ss.getCoin(), coins).getQuote().getUSD().getPrice();
                if (ss.getPercent() < 0 && ss.getPrice() * ss.getPercent() + ss.getPrice() - price >= 0) {
                    telegramBot.execute(new SendMessage().setChatId(userService.findById(ss.getUserId()).getChatId()).setText(createSmartSubscribeText(ss.getCoin(), coins,price)));
                }else if (ss.getPercent() > 0 && ss.getPrice() * ss.getPercent() + ss.getPrice() - price <= 0){
                    telegramBot.execute(new SendMessage().setChatId(userService.findById(ss.getUserId()).getChatId()).setText(createSmartSubscribeText(ss.getCoin(), coins,price)));
                }

            }
        }
    }

    private String createSubscribeText(String coinName, Coins coins) {
        Coins.Bitki.Coin coin = coinsGetInfoService.getCoinByName(coinName, coins);

        return "Криптовалюта - " + coin.getSymbol() + ", " + "\n"
                + "Курс USD - " + coin.getQuote().getUSD().getPrice() + ", " + "\n"
                + "Общее кол-во - " + coin.getTotal_supply() + ", " + "\n"
                + "Изменения за 1 час - " + coin.getQuote().getUSD().getPercent_change_1h() + ", " + "\n"
                + "Изменения за 24 часа - " + coin.getQuote().getUSD().getPercent_change_24h() + ", " + "\n"
                + "Изменения за 7 дней - " + coin.getQuote().getUSD().getPercent_change_7d();
    }
    private String createSmartSubscribeText(String coinName, Coins coins, Double price) {
        Coins.Bitki.Coin coin = coinsGetInfoService.getCoinByName(coinName, coins);

        return "Криптовалюта - " + coin.getSymbol() + ", " + "\n"
                + "Курс USD - " + coin.getQuote().getUSD().getPrice() + ", " + "\n"
                + "Стоимость до - " + price + ", " + "\n";
    }
}

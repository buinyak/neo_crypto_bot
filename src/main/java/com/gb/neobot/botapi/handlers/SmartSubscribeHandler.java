package com.gb.neobot.botapi.handlers;

import com.gb.neobot.Icon;
import com.gb.neobot.appconfig.BotState;
import com.gb.neobot.botapi.InputMessageHandler;
import com.gb.neobot.model.Coins;
import com.gb.neobot.model.SmartSubscribe;
import com.gb.neobot.model.User;
import com.gb.neobot.service.ButtonsService;
import com.gb.neobot.service.CoinsGetInfoService;
import com.gb.neobot.service.SmartSubscribeService;
import com.gb.neobot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class SmartSubscribeHandler implements InputMessageHandler {

    private final UserService userService;
    private final ButtonsService buttonsService;
    private final SmartSubscribeService smartSubscribeService;
    private final CoinsGetInfoService coinsGetInfoService;

    public SmartSubscribeHandler(UserService userService, ButtonsService buttonsService, SmartSubscribeService smartSubscribeService, CoinsGetInfoService coinsGetInfoService) {
        this.userService = userService;
        this.buttonsService = buttonsService;
        this.smartSubscribeService = smartSubscribeService;
        this.coinsGetInfoService = coinsGetInfoService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SMART_SUBSCRIBE;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        User user = userService.findByChatId(inputMsg.getChatId());
        SendMessage message = new SendMessage();
        if (user.getState() == BotState.SMART_SUBSCRIBE.ordinal() && inputMsg.getText().equals("My Subscribes")) {
            user.setState(BotState.MENU.ordinal());
            userService.updateUser(user);
            message = buttonsService.getDeleteSubscribeMessage(user.getId(), inputMsg.getChatId(), Icon.CHART.get()+" Ваши подписки:");
        } else if (user.getState() == BotState.SMART_SUBSCRIBE.ordinal() && inputMsg.getText().equals("Simple Subscribe")) {
            user.setState(BotState.MENU.ordinal());
            userService.updateUser(user);
            message = buttonsService.getCoinsMenuMessage(inputMsg.getChatId(), Icon.BALLOT.get()+" Выберите simple подписку");
        } else if (user.getState() == BotState.SMART_SUBSCRIBE.ordinal()) {

            if (tryParseDouble(inputMsg.getText())) {

                message.setChatId(inputMsg.getChatId())
                        .setText(Icon.TRUE.get()+" Подписка установлена");

                Coins.Bitki.Coin coin = coinsGetInfoService.getCoinByName(user.getSmartSubscribeCoin(), coinsGetInfoService.getAllCoins());
                smartSubscribeService.addSmartSubscribe(new SmartSubscribe(user.getId(), user.getSmartSubscribeCoin(), coin.getQuote().getUSD().getPrice(), Double.parseDouble(inputMsg.getText())));
                user.setState(BotState.MENU.ordinal());
                userService.updateUser(user);
            } else {
                message.setChatId(inputMsg.getChatId())
                        .setText(Icon.EXCLA.get()+" Неверный формат");
            }
        }


        return message;

    }

    boolean tryParseDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

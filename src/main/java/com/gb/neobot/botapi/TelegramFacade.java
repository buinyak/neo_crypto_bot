package com.gb.neobot.botapi;

import com.gb.neobot.Icon;
import com.gb.neobot.appconfig.BotState;
import com.gb.neobot.appconfig.BotStateContext;
import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.model.User;
import com.gb.neobot.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
@EnableScheduling
public class TelegramFacade {
    private final BotStateContext botStateContext;
    private final UserService userService;
    private final SimpleSubscribeService simpleSubscribeService;
    private final SmartSubscribeService smartSubscribeService;

    public TelegramFacade(BotStateContext botStateContext, UserService userService, SimpleSubscribeService simpleSubscribeService, SmartSubscribeService smartSubscribeService) {
        this.botStateContext = botStateContext;
        this.userService = userService;
        this.simpleSubscribeService = simpleSubscribeService;
        this.smartSubscribeService = smartSubscribeService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            log.info("New message from User:{}, chatId: {}, with text {}",
                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        BotState botState;
        SendMessage replyMessage;
        User user = userService.findByChatId(message.getChatId());

        if (user == null || inputMsg.equals("/start")) {
            botState = BotState.START;
        } else {
            botState = BotState.byId(user.getState());
        }

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        User user = userService.findByChatId(chatId);
        int userId = user.getId();
        String buttonQueryData = buttonQuery.getData();
        BotApiMethod<?> callBackAnswer = new SendMessage(chatId, "Вы уже подписаны на выбранную криптовалюту");


        if (buttonQueryData.substring(0, 6).equals("delete")) {
            if (buttonQueryData.substring(0, buttonQueryData.indexOf('_')).equals("deleteSimpleSubscribe") && simpleSubscribeService.findByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1)) != null) {
                callBackAnswer = new SendMessage(chatId, Icon.X.get()+" Подписка на " + buttonQueryData.substring(buttonQueryData.indexOf('_') + 1) + " - отменена");
                simpleSubscribeService.deleteByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1));
            } else if (buttonQueryData.substring(0, buttonQueryData.indexOf('_')).equals("deleteSmartSubscribe") && smartSubscribeService.findByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1)) != null) {
                callBackAnswer = new SendMessage(chatId, Icon.X.get()+" Подписка на " + buttonQueryData.substring(buttonQueryData.indexOf('_') + 1) + " - отменена");
                smartSubscribeService.deleteByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1));
            }else {
                callBackAnswer = new SendMessage(chatId, "Вы не подписаны на " + buttonQueryData.substring(buttonQueryData.indexOf('_') + 1));
            }
        } else if (buttonQueryData.substring(0, 6).equals("button")) {
            if (buttonQueryData.substring(0, buttonQueryData.indexOf('_')).equals("buttonSimpleSubscribe") && simpleSubscribeService.findByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1)) == null) {
                callBackAnswer = new SendMessage(chatId, Icon.TRUE.get()+" Подписка на " + buttonQueryData.substring(buttonQueryData.indexOf('_') + 1) + "- активирована");
                simpleSubscribeService.addSimpleSubscribe(new SimpleSubscribe(user.getId(), buttonQueryData.substring(buttonQueryData.indexOf('_') + 1)));

            }else if (buttonQueryData.substring(0, buttonQueryData.indexOf('_')).equals("buttonSmartSubscribe") && simpleSubscribeService.findByUserIdAndCoin(userId, buttonQueryData.substring(buttonQueryData.indexOf('_') + 1)) == null) {
                callBackAnswer = new SendMessage(chatId, "Установите для " + buttonQueryData.substring(buttonQueryData.indexOf('_') + 1) + " - % отклонение от курса");
                user.setSmartSubscribeCoin(buttonQueryData.substring(buttonQueryData.indexOf('_') + 1));
                userService.updateUser(user);
            }
        }
        return callBackAnswer;

    }
}
package com.gb.neobot.botapi.handlers;

import com.gb.neobot.Icon;
import com.gb.neobot.appconfig.BotState;
import com.gb.neobot.botapi.InputMessageHandler;
import com.gb.neobot.model.User;
import com.gb.neobot.service.ButtonsService;
import com.gb.neobot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class MenuHandler implements InputMessageHandler {

    private final UserService userService;
    private final ButtonsService buttonsService;

    public MenuHandler(UserService userService, ButtonsService buttonsService) {
        this.userService = userService;
        this.buttonsService = buttonsService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.MENU;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        User user = userService.findByChatId(inputMsg.getChatId());

        SendMessage message;


        if (user.getState() == BotState.MENU.ordinal() && inputMsg.getText().equals("Simple Subscribe")) {
            user.setState(BotState.MENU.ordinal());
            userService.updateUser(user);
            message = buttonsService.getCoinsMenuMessage(inputMsg.getChatId(), Icon.BALLOT.get()+" Выберите simple подписку");

        } else if (user.getState() == BotState.MENU.ordinal() && inputMsg.getText().equals("Smart Subscribe")) {
            user.setState(BotState.SMART_SUBSCRIBE.ordinal());
            userService.updateUser(user);
            message = buttonsService.getSmartSubscribeMessage(inputMsg.getChatId(), Icon.BALLOT.get()+" Выберите криптовалюту и установите % отклонения от курса ");
        } else if (user.getState() == BotState.MENU.ordinal() && inputMsg.getText().equals("My Subscribes")) {
            user.setState(BotState.MENU.ordinal());
            userService.updateUser(user);
            message = buttonsService.getDeleteSubscribeMessage(user.getId(), inputMsg.getChatId(), Icon.CHART.get()+" Ваши подписки:");
        } else {
            user.setState(BotState.MENU.ordinal());
            message = buttonsService.getMainMenuMessage(inputMsg.getChatId(), Icon.PIN.get()+" Используйте кнопки");
        }
        return message;

    }

}

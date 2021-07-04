package com.gb.neobot.botapi.handlers;

import com.gb.neobot.Icon;
import com.gb.neobot.appconfig.BotState;
import com.gb.neobot.botapi.InputMessageHandler;
import com.gb.neobot.model.User;
import com.gb.neobot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class StartHandler implements InputMessageHandler {

    private final UserService userService;
    public StartHandler(UserService userService){
        this.userService = userService;
    }
    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.START;
    }
    private SendMessage processUsersInput(Message inputMsg){

        User user = userService.findByChatId(inputMsg.getChatId());
        BotState state = BotState.MENU;
        if(user == null){
            userService.addUser(new User(inputMsg.getChatId(),1));
            log.info("New message from User:{}, chatId: {}, with text {}, ordinal {}",
                    inputMsg.getFrom().getUserName(), inputMsg.getChatId(), inputMsg.getText(),state.ordinal());
        }
        return new SendMessage()
                .setChatId(inputMsg.getChatId())
                .setText("Здравствуйте "+ inputMsg.getFrom().getUserName()+", мое имя Tetatet"+ Icon.ROBOT.get()+". Я готов помочь Вам с мониторингом стоймости криптовалют. Начнем?");

    }
}

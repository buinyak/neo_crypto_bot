package com.gb.neobot.service;

import com.gb.neobot.Icon;
import com.gb.neobot.model.SimpleSubscribe;
import com.gb.neobot.model.SmartSubscribe;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;

@Service
public class ButtonsService {
    private final SimpleSubscribeService simpleSubscribeService;
    private final SmartSubscribeService smartSubscribeService;

    public ButtonsService(SimpleSubscribeService simpleSubscribeService, SmartSubscribeService smartSubscribeService) {
        this.simpleSubscribeService = simpleSubscribeService;
        this.smartSubscribeService = smartSubscribeService;
    }

    public SendMessage getMainMenuMessage(final long chatId, final String textMessage) {
        final ReplyKeyboardMarkup replyKeyboardMarkup = getMainMenuKeyboard();

        return createMessageWithKeyboard(chatId, textMessage, replyKeyboardMarkup);
    }

    public SendMessage getCoinsMenuMessage(final long chatId, final String textMessage) {
        final InlineKeyboardMarkup inlineKeyboardMarkup = getCoinsMenuKeyboard();
        return createMessageWithInlineKeyboard(chatId, textMessage, inlineKeyboardMarkup);
    }

    public SendMessage getDeleteSubscribeMessage(int userId, final long chatId, final String textMessage) {
        List<SimpleSubscribe> simpleSubscribes = simpleSubscribeService.findAllByUserId(userId);
        List<SmartSubscribe> smartSubscribes = smartSubscribeService.findAllByUserId(userId);
        final InlineKeyboardMarkup inlineKeyboardMarkup = getDeleteSubscribeKeyboard(simpleSubscribes, smartSubscribes);
        return createMessageWithInlineKeyboard(chatId, textMessage, inlineKeyboardMarkup);
    }

    public SendMessage getSmartSubscribeMessage(final long chatId, final String textMessage) {
        final InlineKeyboardMarkup SmartSubscribeKeyboardMarkup = getSmartSubscribeKeyBoard();
        return createMessageWithInlineKeyboard(chatId, textMessage, SmartSubscribeKeyboardMarkup);
    }

    private InlineKeyboardMarkup getSmartSubscribeKeyBoard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonBTC = new InlineKeyboardButton().setText("BTC");
        InlineKeyboardButton buttonETH = new InlineKeyboardButton().setText("ETH");
        InlineKeyboardButton buttonBNB = new InlineKeyboardButton().setText("BNB");
        InlineKeyboardButton buttonDOGE = new InlineKeyboardButton().setText("DOGE");
        InlineKeyboardButton buttonDOT = new InlineKeyboardButton().setText("DOT");
        InlineKeyboardButton buttonADA = new InlineKeyboardButton().setText("ADA");

        //Every button must have callBackData, or else not work !
        buttonBTC.setCallbackData("buttonSmartSubscribe_BTC");
        buttonETH.setCallbackData("buttonSmartSubscribe_ETH");
        buttonBNB.setCallbackData("buttonSmartSubscribe_BNB");
        buttonDOGE.setCallbackData("buttonSmartSubscribe_DOGE");
        buttonDOT.setCallbackData("buttonSmartSubscribe_DOT");
        buttonADA.setCallbackData("buttonSmartSubscribe_ADA");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonBTC);
        keyboardButtonsRow1.add(buttonETH);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonBNB);
        keyboardButtonsRow2.add(buttonDOGE);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonDOT);
        keyboardButtonsRow2.add(buttonADA);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getCoinsMenuKeyboard() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonBTC = new InlineKeyboardButton().setText("BTC");
        InlineKeyboardButton buttonETH = new InlineKeyboardButton().setText("ETH");
        InlineKeyboardButton buttonBNB = new InlineKeyboardButton().setText("BNB");
        InlineKeyboardButton buttonDOGE = new InlineKeyboardButton().setText("DOGE");
        InlineKeyboardButton buttonDOT = new InlineKeyboardButton().setText("DOT");
        InlineKeyboardButton buttonADA = new InlineKeyboardButton().setText("ADA");

        //Every button must have callBackData, or else not work !
        buttonBTC.setCallbackData("buttonSimpleSubscribe_BTC");
        buttonETH.setCallbackData("buttonSimpleSubscribe_ETH");
        buttonBNB.setCallbackData("buttonSimpleSubscribe_BNB");
        buttonDOGE.setCallbackData("buttonSimpleSubscribe_DOGE");
        buttonDOT.setCallbackData("buttonSimpleSubscribe_DOT");
        buttonADA.setCallbackData("buttonSimpleSubscribe_ADA");


        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        keyboardButtonsRow1.add(buttonBTC);
        keyboardButtonsRow1.add(buttonETH);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonBNB);
        keyboardButtonsRow2.add(buttonDOGE);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonDOT);
        keyboardButtonsRow2.add(buttonADA);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private ReplyKeyboardMarkup getMainMenuKeyboard() {


        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();

        row1.add(new KeyboardButton("My Subscribes"));
        row2.add(new KeyboardButton("Smart Subscribe"));
        row3.add(new KeyboardButton("Simple Subscribe"));
        List<KeyboardRow> rowList = new ArrayList<>();

        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);

        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    private InlineKeyboardMarkup getDeleteSubscribeKeyboard(List<SimpleSubscribe> simpleSubscribes, List<SmartSubscribe> smartSubscribes) {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1;
        InlineKeyboardButton buttonDeleteSubscribe;
        for (SimpleSubscribe ss : simpleSubscribes) {
            buttonDeleteSubscribe = new InlineKeyboardButton().setText(ss.getCoin() + " - Отменить подписку "+ Icon.X.get());
            buttonDeleteSubscribe.setCallbackData("deleteSimpleSubscribe_" + ss.getCoin());
            keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(buttonDeleteSubscribe);
            rowList.add(keyboardButtonsRow1);
        }
        for (SmartSubscribe ss : smartSubscribes) {
            buttonDeleteSubscribe = new InlineKeyboardButton().setText(ss.getCoin() + " |" + ss.getPercent() + "%| - Отменить подписку "+ Icon.X.get());
            buttonDeleteSubscribe.setCallbackData("deleteSmartSubscribe_" + ss.getCoin());
            keyboardButtonsRow1 = new ArrayList<>();
            keyboardButtonsRow1.add(buttonDeleteSubscribe);
            rowList.add(keyboardButtonsRow1);
        }
        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

    private SendMessage createMessageWithInlineKeyboard(final long chatId,
                                                        String textMessage,
                                                        final InlineKeyboardMarkup inlineKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (inlineKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        }
        return sendMessage;
    }

    private SendMessage createMessageWithKeyboard(final long chatId,
                                                  String textMessage,
                                                  final ReplyKeyboardMarkup replyKeyboardMarkup) {
        final SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(textMessage);
        if (replyKeyboardMarkup != null) {
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        }
        return sendMessage;
    }
}

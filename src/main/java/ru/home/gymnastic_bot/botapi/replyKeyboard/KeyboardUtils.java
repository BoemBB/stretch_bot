package ru.home.gymnastic_bot.botapi.replyKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardUtils {
    public ReplyKeyboardMarkup getReplyKeyboardMarkup1Button(String text){
        ReplyKeyboardMarkup replyKeyboardMarkup = getOneTimeKeyboard();

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(text));
        keyboard.add(row1);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup4Button(String _1buttonText, String _2buttonText,
                                                              String _3buttonText, String _4buttonText){
        ReplyKeyboardMarkup replyKeyboardMarkup = getManyTimeKeyboard();
        List<KeyboardRow> keyboard = getKeyBoardRow(_1buttonText, _2buttonText, _3buttonText, _4buttonText);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getOneTimeKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getManyTimeKeyboard(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        return replyKeyboardMarkup;
    }

    public ArrayList<KeyboardRow> getKeyBoardRow(String ... buttonText){
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();

        int j = 0;
        for (int i = 0; i < buttonText.length / 2; i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(new KeyboardButton(buttonText[j++]));
            row.add(new KeyboardButton(buttonText[j++]));
            keyboardRows.add(row);
        }
        return keyboardRows;
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup2Button(String _1buttonText, String _2buttonText){
        ReplyKeyboardMarkup replyKeyboardMarkup = getOneTimeKeyboard();

        List<KeyboardRow> keyboard = getKeyBoardRow(_1buttonText, _2buttonText);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}

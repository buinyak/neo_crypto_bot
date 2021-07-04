package com.gb.neobot;

import com.vdurmont.emoji.EmojiParser;

public enum Icon {
    X(":x:"),
    ROBOT(":robot_face:"),
    PIN(":pushpin:"),
    CHART(":bar_chart:"),
    BALLOT(":ballot_box_with_check:"),
    TRUE(":white_check_mark:"),
    EXCLA(":exclamation:");


    private String value;

    public String get() {
        return EmojiParser.parseToUnicode(value);
    }

    Icon(String value) {
        this.value = value;
    }
    }
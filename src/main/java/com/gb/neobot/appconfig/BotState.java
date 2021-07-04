package com.gb.neobot.appconfig;

public enum BotState {
    START,
    MENU,
    SMART_SUBSCRIBE;
    private static BotState[] states;

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }

        return states[id];
    }
}


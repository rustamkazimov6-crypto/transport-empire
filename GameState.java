package com.transportgame.core;

public class GameState {

    public enum Screen {
        MAIN_MENU,
        NEW_GAME,
        LOAD_GAME,
        SETTINGS,
        PLAYING
    }

    private Screen currentScreen = Screen.MAIN_MENU;
    private String playerCompanyName = "My Transport Co.";
    private long playerCash = 100_000;

    public Screen getCurrentScreen() { return currentScreen; }
    public void setCurrentScreen(Screen screen) { this.currentScreen = screen; }

    public String getPlayerCompanyName() { return playerCompanyName; }
    public void setPlayerCompanyName(String name) { this.playerCompanyName = name; }

    public long getPlayerCash() { return playerCash; }
    public void setPlayerCash(long cash) { this.playerCash = cash; }
}

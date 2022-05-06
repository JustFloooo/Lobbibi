package net.problemzone.lobbibi.util;

import org.bukkit.ChatColor;

public enum Language {

    PLAYER_JOIN(ChatColor.GREEN + "» " + ChatColor.WHITE),
    PLAYER_LEAVE(ChatColor.RED + "« " + ChatColor.WHITE),
    PLAYERS_NEEDED("Es werden noch " + ChatColor.RED + "%d" + ChatColor.GRAY + " weitere Spieler benötigt!"),
    PLAYERS_NEEDED_ONE("Es wird noch " + ChatColor.RED + "1" + ChatColor.GRAY + " weiterer Spieler benötigt!"),

    GAME_START("Das Spiel startet in: " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden"),
    GAME_START_TITLE(ChatColor.RED + "SPIEL " + ChatColor.GRAY + "startet in: " + ChatColor.WHITE),
    GAME_START_CANCEL("Der Spielstart wurde manuell " + ChatColor.RED + "abgebrochen"),

    JNR_START("Du hast den Parkour begonnen"),
    JNR_FAIL("Du hast " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sprünge in " + ChatColor.YELLOW + "%,.2f" + ChatColor.GRAY + " Sekunden geschafft"),
    JNR_AVERAGE("Dies entspricht durchschnittlich "+ ChatColor.YELLOW + "%,.2f" + ChatColor.GRAY + " Sprüngen pro Minute");

    private static final String SYSTEM_PREFIX = ChatColor.GOLD + "Lobby " + ChatColor.DARK_GRAY + "» ";

    private final String text;

    Language(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getFormattedText() {
        return SYSTEM_PREFIX + ChatColor.GRAY + text;
    }

}

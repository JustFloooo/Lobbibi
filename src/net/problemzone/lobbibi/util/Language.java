package net.problemzone.lobbibi.util;

import org.bukkit.ChatColor;

public enum Language {

    JNR_START("Du hast den Parkour begonnen"),
    JNR_FAIL("Du hast " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sprünge in " + ChatColor.YELLOW + "%d" + ChatColor.GRAY + " Sekunden geschafft"),
    JNR_AVERAGE("Dies entspricht durchschnittlich "+ ChatColor.YELLOW + "%,.2f" + ChatColor.GRAY + " Sprüngen pro Minute");

    private static final String SYSTEM_PREFIX = ChatColor.GOLD + "System " + ChatColor.DARK_GRAY + "» ";

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

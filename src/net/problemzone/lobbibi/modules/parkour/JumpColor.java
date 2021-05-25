package net.problemzone.lobbibi.modules.parkour;

import org.bukkit.Material;

import java.util.List;

public enum JumpColor {

    WHITE(Material.WHITE_WOOL, Material.WHITE_TERRACOTTA),
    ORANGE(Material.ORANGE_WOOL, Material.ORANGE_TERRACOTTA),
    MAGENTA(Material.MAGENTA_WOOL, Material.MAGENTA_TERRACOTTA),
    LIGHT_BLUE(Material.LIGHT_BLUE_WOOL, Material.LIGHT_BLUE_TERRACOTTA),
    YELLOW(Material.YELLOW_WOOL, Material.YELLOW_TERRACOTTA),
    LIME(Material.LIME_WOOL, Material.LIME_TERRACOTTA),
    PINK(Material.PINK_WOOL, Material.PINK_TERRACOTTA),
    GRAY(Material.GRAY_WOOL, Material.GRAY_TERRACOTTA),
    LIGHT_GRAY(Material.LIGHT_GRAY_WOOL, Material.LIGHT_GRAY_TERRACOTTA),
    CYAN(Material.CYAN_WOOL, Material.CYAN_TERRACOTTA),
    PURPLE(Material.PURPLE_WOOL, Material.PURPLE_TERRACOTTA),
    BLUE(Material.BLUE_WOOL, Material.BLUE_TERRACOTTA),
    BROWN(Material.BROWN_WOOL, Material.BROWN_TERRACOTTA),
    GREEN(Material.GREEN_WOOL, Material.GREEN_TERRACOTTA),
    RED(Material.RED_WOOL, Material.RED_TERRACOTTA),
    BLACK(Material.BLACK_WOOL, Material.BLACK_TERRACOTTA);

    private static final List<JumpColor> VALUES =
            List.of(values());

    private final Material wool;
    private final Material clay;

    JumpColor(Material wool, Material clay) {
        this.wool = wool;
        this.clay = clay;
    }

    public Material getWool() {
        return wool;
    }

    public Material getClay() {
        return clay;
    }

    public static JumpColor getNewColor(){
        return List.of(values()).stream().skip((int) (Math.random() * values().length)).findFirst().orElse(JumpColor.WHITE);
    }
}

package net.problemzone.lobbibi.util;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public enum Sounds {

    JUMP_SUCCESS(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2),
    CLICK_TIMER(Sound.BLOCK_NOTE_BLOCK_HAT, 1),
    CLICK_TIMER_END(Sound.BLOCK_NOTE_BLOCK_HARP, 2);

    private final Sound sound;
    private final float pitch;

    Sounds(Sound sound, float pitch) {
        this.sound = sound;
        this.pitch = pitch;
    }

    public void playSoundForPlayer(Player player){
        player.playSound(player.getLocation(), sound, SoundCategory.AMBIENT, 1, pitch);
    }


}

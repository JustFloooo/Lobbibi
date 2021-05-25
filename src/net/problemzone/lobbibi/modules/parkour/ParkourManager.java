package net.problemzone.lobbibi.modules.parkour;

import net.problemzone.lobbibi.util.Language;
import net.problemzone.lobbibi.util.Sounds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ParkourManager {

    private static final Material PRESSURE_PLATE_MATERIAL = Material.LIGHT_WEIGHTED_PRESSURE_PLATE;
    private static final Material BELOW_BLOCK_MATERIAL = Material.NETHERITE_BLOCK;

    Map<Player, JumpSequence> jumps = new HashMap<>();

    public Location newParkour(Player player) {

        if (jumps.containsKey(player)) cancelParkour(player);
        player.sendMessage(Language.JNR_START.getFormattedText());
        jumps.put(player, new JumpSequence(player.getLocation().getBlock()));
        return jumps.get(player).getCurrentBlock().getRelative(BlockFace.UP).getLocation();

    }

    public void generateNewBlock(Player player) {
        Sounds.JUMP_SUCCESS.playSoundForPlayer(player);
        jumps.get(player).newJump();
    }

    public Block getPlayerTarget(Player player) {
        return jumps.get(player).getTargetBlock();
    }

    public boolean hasPlayer(Player player) {
        return jumps.containsKey(player);
    }

    public Location cancelParkour(Player player) {
        double duration = (double) (System.currentTimeMillis() - jumps.get(player).getStartTime()) / 1000;
        player.sendMessage(String.format(Language.JNR_FAIL.getFormattedText(), jumps.get(player).getJumpCount(), duration));
        player.sendMessage(String.format(Language.JNR_AVERAGE.getFormattedText(), (double) jumps.get(player).getJumpCount() * 60 / duration));
        jumps.get(player).removeBlocks();
        jumps.remove(player);
        return Objects.requireNonNull(player.getLocation().getWorld()).getSpawnLocation();
    }

    public boolean isStartBlock(Block block) {
        if (block.getType() != PRESSURE_PLATE_MATERIAL) return false;
        return block.getRelative(BlockFace.DOWN).getType() == BELOW_BLOCK_MATERIAL;
    }

    public void removeAllBlocks(){
        jumps.values().forEach(JumpSequence::removeBlocks);
    }

    private static class JumpSequence {

        private final long startTime;
        private final JumpColor jumpColor;

        private int jumpCount;

        private Block currentBlock;
        private Block nextBlock;

        private JumpSequence(Block block) {
            startTime = System.currentTimeMillis();
            jumpColor = JumpColor.getNewColor();
            jumpCount = -1;

            generateStartBlock(block);
            generateNextBlock();
        }

        private void generateStartBlock(Block block) {
            Random random = new Random();
            int x = random.nextInt(40) - 20;
            int z = 8 + random.nextInt(15);

            currentBlock = block.getRelative(x, 0, -z);
            currentBlock.setType(jumpColor.getClay());
        }

        private void newJump() {
            currentBlock.setType(Material.AIR);
            currentBlock = nextBlock;
            currentBlock.setType(jumpColor.getClay());
            generateNextBlock();
        }

        private void generateNextBlock() {
            Random random = new Random();
            int x = 2 + random.nextInt(3);
            int y = random.nextInt(2);
            int z = random.nextInt(7) - 3;
            int dir = random.nextInt(4);

            if ((z == -3 || z == 3) && x > 2 || (z == 2 || z == -2) && x == 4) y = 0;

            switch (dir) {
                case 0:
                    nextBlock = currentBlock.getRelative(x, y, z);
                    break;
                case 1:
                    nextBlock = currentBlock.getRelative(-x, y, z);
                    break;
                case 2:
                    nextBlock = currentBlock.getRelative(z, y, x);
                    break;
                case 3:
                    nextBlock = currentBlock.getRelative(z, y, -x);
                    break;
            }

            if (nextBlock.getType() != Material.AIR) {
                generateNextBlock();
                return;
            }

            jumpCount++;
            nextBlock.setType(jumpColor.getWool());
        }

        private void removeBlocks() {
            if (currentBlock != null) currentBlock.setType(Material.AIR);
            if (nextBlock != null) nextBlock.setType(Material.AIR);
        }

        private Block getCurrentBlock() {
            return currentBlock;
        }

        private Block getTargetBlock() {
            return nextBlock;
        }

        private long getStartTime() {
            return startTime;
        }

        private int getJumpCount() {
            return jumpCount;
        }
    }

}

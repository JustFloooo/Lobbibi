package net.problemzone.lobbibi.modules.parkour;

import net.problemzone.lobbibi.util.Language;
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

    private static final Material material = Material.CYAN_WOOL;

    Map<Player, Jump> jumps = new HashMap<>();

    public Location newParkour(Player player) {

        if (jumps.containsKey(player)) cancelParkour(player);
        player.sendMessage(Language.JNR_START.getFormattedText());
        jumps.put(player, new Jump(player.getLocation().getBlock()));
        return jumps.get(player).getCurrentBlock().getRelative(BlockFace.UP).getLocation();

    }

    public void generateNewBlock(Player player) {
        jumps.get(player).newJump();
    }

    public Block getPlayerTarget(Player player) {
        return jumps.get(player).getTargetBlock();
    }

    public boolean hasPlayer(Player player) {
        return jumps.containsKey(player);
    }

    public Location cancelParkour(Player player) {
        long duration = (System.currentTimeMillis() - jumps.get(player).getStartTime()) / 1000;
        player.sendMessage(String.format(Language.JNR_FAIL.getFormattedText(), jumps.get(player).getJumpCount(), duration));
        player.sendMessage(String.format(Language.JNR_AVERAGE.getFormattedText(), (double)jumps.get(player).getJumpCount()*60/duration));
        jumps.get(player).removeBlocks();
        jumps.remove(player);
        return Objects.requireNonNull(player.getLocation().getWorld()).getSpawnLocation();
    }

    public boolean isStartBlock(Block block) {
        if (block.getType() != Material.LIGHT_WEIGHTED_PRESSURE_PLATE) return false;
        return block.getRelative(BlockFace.DOWN).getType() == Material.NETHERITE_BLOCK;
    }

    private static class Jump {

        private final long startTime = System.currentTimeMillis();
        private int jumpCount = 0;

        private Block currentBlock;
        private Block nextBlock;

        private Jump(Block block) {
            Random random = new Random();
            int x = random.nextInt(40) - 20;
            int z = 8 + random.nextInt(15);

            currentBlock = block.getRelative(x, 0, -z);
            currentBlock.setType(Material.RED_WOOL);
            generateBlock();
        }

        private void newJump() {
            currentBlock.setType(Material.AIR);
            currentBlock = nextBlock;
            currentBlock.setType(Material.RED_WOOL);
            generateBlock();
        }

        private void generateBlock() {
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
                generateBlock();
                return;
            }

            jumpCount++;
            nextBlock.setType(material);
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

        public long getStartTime() {
            return startTime;
        }

        public int getJumpCount() {
            return jumpCount;
        }
    }

}

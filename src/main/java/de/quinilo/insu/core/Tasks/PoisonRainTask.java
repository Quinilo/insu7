package de.quinilo.insu.core.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Level;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.event.EventType;

public class PoisonRainTask extends Task {

    @Override
    public void onRun(int i) {
        if (PluginMain.getInstance().getActivatedEvents().contains(EventType.POISON_RAIN)) {
            for (Level l : PluginMain.getInstance().getServer().getLevels().values()) l.setRaining(true);
            for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                boolean damage = true;
                for (int y = (int) p.getLevelBlock().getY(); y <= 350; y++) {
                    if (p.getLevel().getBlock((int) p.getLevelBlock().getX(), y, (int) p.getLevelBlock().getZ()).getId() != (Block.get(BlockID.AIR).getId())) {
                        damage = false;
                    }
                }
                if (damage) {
                    p.addEffect(Effect.getEffect(Effect.POISON).setDuration(100));
                }
            }
        }
    }
}

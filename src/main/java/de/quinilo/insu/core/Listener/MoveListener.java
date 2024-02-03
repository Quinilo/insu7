package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.event.EventType;

public class MoveListener implements Listener {
    private PluginMain plugin;

    public MoveListener(PluginMain plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (PluginMain.getInstance().getActivatedEvents().contains(EventType.POISON_WATER)) {
            if (player.isInsideOfWater()
                    || player.getLocation().getLevelBlock().getId() == Block.get(BlockID.WATER).getId()
                    || player.getLocation().getLevelBlock().getId() == Block.get(BlockID.STILL_WATER).getId()) {
                player.addEffect(Effect.getEffect(Effect.POISON).setDuration(100));
            }
        }
    }
}


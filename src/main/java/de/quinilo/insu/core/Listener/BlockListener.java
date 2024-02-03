package de.quinilo.insu.core.Listener;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.level.Sound;

public class BlockListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getLocation().getY() >= 185) {
            e.setCancelled(true);
            e.getBlock().getLevel().addSound(e.getBlock().getLocation(), Sound.NOTE_BASS, 1f, 1f, e.getPlayer());
            e.getBlock().getLevel().addParticleEffect(e.getBlock().getLocation(), ParticleEffect.CAMPFIRE_SMOKE);
        }
    }
}

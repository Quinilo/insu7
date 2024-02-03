
package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.lang.TranslationContainer;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());

        String msg = "error";
        EntityDamageEvent ev = e.getEntity().getLastDamageCause();

        if (ev instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) ev).getDamager();
            msg = "§c" + damager.getName() + " hat " + p.getName() + " getötet";
        } else {
            msg = "§c" + p.getName() + " ist gestorben";
        }

        if (ip != null) {
            ip.setDead(true);
            p.kick(
                    "" +
                    "§l§4Du bist gestorben:\n" +
                    "§r§c" + msg + "\n" +
                    "§r§l§aVielen Dank für deine Teilnahme",
                    false);
            e.setDeathMessage(PluginMain.getInstance().getPrefix() + msg);
            PluginMain.getInstance().getDataConfig().saveData();
        } else {
            e.setCancelled(true);
            p.sendMessage(PluginMain.getInstance().getPrefix() + "§cDu kannst nicht sterben, da du kein regestrierter Spieler bist");
        }
    }
}


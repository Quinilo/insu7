package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DamageListener implements Listener {

    List<Player> showUiCooldown = new ArrayList<>();

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        Player p = (Player) e.getDamager();

        if (e.getEntity() instanceof Player) {
            Player entity = (Player) e.getEntity();

            for (Player pp : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                InsuPlayer ip = PlayerManager.getInsuPlayer(pp.getName());
                if (ip != null && ip.getRank().equals(Rank.GAME_MASTER) || ip != null && ip.getRank().equals(Rank.ADMIN)) {
                    if (!showUiCooldown.contains(p) && !showUiCooldown.contains(entity)) {
                        pp.showFormWindow(new FormWindowModal("§l§4Achtung!", "§7Der Spieler §c" + p.getName() + " §7hat §c" + e.getEntity().getName() + " §7angegriffen\n\n§7Möchtest du dich zu ihm teleportieren?", "§7Teleport zu §a" + p.getName(), "§cNöö"));
                        showUiCooldown.add(p);
                        showUiCooldown.add(entity);

                        removeFromCooldownWithDelay(p);
                        removeFromCooldownWithDelay(entity);
                    }
                }
            }

            Player t = (Player) e.getEntity();
            InsuPlayer ipp = PlayerManager.getInsuPlayer(p.getName());
            InsuPlayer ipt = PlayerManager.getInsuPlayer(t.getName());

            if (ipp != null && ipt != null) {
                if (ipp.getTeam().equals(ipt.getTeam())) {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§4ALTER! §cKlatsch doch nicht deinen Mate");
                    e.setCancelled(true);
                    return;
                }
            }

            if (p.getHealth() - e.getDamage() < 0) {
                PluginMain.getInstance().customDeathMessages.put(t, "§e" + t.getName() + " §7wurde von §c" + e.getDamager().getName() + " §7getötet");
            }
        }
    }

    public void removeFromCooldownWithDelay(Player p) {
        PluginMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                showUiCooldown.remove(p);
            }
        }, 500);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            for (Player pp : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                InsuPlayer ip = PlayerManager.getInsuPlayer(pp.getName());
                if (ip != null && ip.getRank().equals(Rank.GAME_MASTER) || ip != null && ip.getRank().equals(Rank.ADMIN)) {
                    pp.sendMessage(PluginMain.getInstance().getPrefix() + "§cDer Spieler §e" + p.getName() + " §chat gerade Schaden genommen");
                }
            }
        }

        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (p.getHealth() - e.getDamage() < 0) {
                PluginMain.getInstance().customDeathMessages.put(p, "§e" + p.getName() + " §7ist an §c" + e.getCause() + " §7gestorben");
            }
        }
    }
}

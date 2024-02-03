package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;
import de.quinilo.insu.core.event.EventType;

import java.util.Objects;
import java.util.UUID;

public class ConnectionListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());

        if (ip != null && ip.getRank().equals(Rank.PLAYER)) {
            if (!PluginMain.getInstance().getCurrentTime().split(":")[0].equals(PluginMain.getInstance().getStartTime())) {
                p.kick("§4Leider ist gerade keine Spielzeit :o\n§cDu kannst wieder um §e" + PluginMain.getInstance().getStartTime() + " Uhr §closlegen", false);
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());
        String role = "§cerror";

        p.removeAllEffects();

        if (ip == null) {
            //Wichtig & Richtig
            if (p.getName().equals("Quinilo")) {
                PlayerManager.getTeam("staffTeam").players.add(new InsuPlayer("Quinilo", UUID.fromString("6ba7e0c3-ab25-31d7-8d9e-9c0cbc6c339f"), false, "staffTeam", Rank.ADMIN));
                PluginMain.getInstance().getDataConfig().saveData();
                PluginMain.getInstance().getServer().addOp("Quinilo");
            }


            PlayerManager.addSpectator(p);
            e.setJoinMessage("");
            role = "§7Zuschauer";

        } else if (ip.isDead()){
            p.kick("§4Leider bist du gestorben :c\n§cVilleicht beim nächsten mal...", false);
            e.setJoinMessage("");
            return;

        } else {
            if (ip.getRank().equals(Rank.ADMIN)) {
                PlayerManager.addSpectator(p);
                role = "§4Admin";
                e.setJoinMessage("");
            } else if (ip.getRank().equals(Rank.GAME_MASTER)) {
                PlayerManager.addSpectator(p);
                role = "§cGame-Master";
                e.setJoinMessage("");
                e.getPlayer().getInventory().setItem(5, Item.get(ItemID.COMPASS).setCustomName("§8» §l§cTeleporter"));
            } else {
                role = "§aSpieler";
                e.setJoinMessage(PluginMain.getInstance().getPrefix() + "§a" + p.getName() + " §7hat den Server betreten");

                p.addEffect(Effect.getEffect(Effect.RESISTANCE).setDuration(300).setVisible(false).setAmplifier(300));
                p.setGamemode(0);

                if (ip.getUuid() == null) {
                    if (PlayerManager.getTeam(ip.getTeam()).getX() == 0 &&
                            PlayerManager.getTeam(ip.getTeam()).getY() == 0 &&
                            PlayerManager.getTeam(ip.getTeam()).getZ() == 0) {
                        p.kick("§cEin Fehler ist aufgetreten:\n§eFür dein Team(" + ip.getTeam() + ") wurde kein Spawnpoint gesetzt\n\n§a-> Bitte kontaktiere die Projektleitung", false);
                        e.setJoinMessage("");
                        return;
                    }
                    p.teleport(new Vector3(
                            PlayerManager.getTeam(ip.getTeam()).getX(),
                            PlayerManager.getTeam(ip.getTeam()).getY(),
                            PlayerManager.getTeam(ip.getTeam()).getZ()));
                    ip.setUuid(p.getUniqueId());

                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§aHerzlich Wilkommen zu INSU");
                }

                String date = PluginMain.getInstance().getCurrentDate();
                StringBuilder correctDate = new StringBuilder();
                int i = 0;
                for (String s : date.split("\\.")) {
                    i++;
                    if (s.length() == 1) {
                         correctDate.append("0").append(s);
                    } else {
                        correctDate.append(s);
                    }
                    if (i != 3) correctDate.append(".");
                }
                ip.setLastLogin(correctDate.toString());

                if (PluginMain.getInstance().getActivatedEvents().contains(EventType.BLINDNESS)) {
                    p.addEffect(Effect.getEffect(Effect.BLINDNESS).setDuration(100000).setVisible(false).setAmplifier(1));
                }
            }
        }

        p.sendTitle("§7Du bist", "§l" + role);

        PluginMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                p.sendTitle(PluginMain.getInstance().getCurrentDayString(PluginMain.getInstance().getStartDate()), PluginMain.getInstance().getPrefix());
            }
        }, 200);

        PluginMain.getInstance().getDataConfig().saveData();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        InsuPlayer ip = PlayerManager.getInsuPlayer(e.getPlayer().getName());
        if (ip == null || ip.isDead()) {
            e.setQuitMessage("");
            return;
        }
        e.setQuitMessage(PluginMain.getInstance().getPrefix() + "§7Der Spieler §e" + e.getPlayer().getName() + " §7hat den Server verlassen");
    }
}

package de.quinilo.insu.core.event;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Level;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

import java.util.*;

public class EventManager {

    public static void executeEvent(EventType eventType) {

        PluginMain.getInstance().brodcastMessage("§l§eEvent: §r§g" + eventType.displayName);
        PluginMain.getInstance().brodcastMessage("§8-> §7" + eventType.description);
        PluginMain.getInstance().brodcastMessage("§8-> §7Du kannst mit §a/insu §7alle Informationen jeder zeit einsehen");
        PluginMain.getInstance().brodcastMessage("§8-> §eDas Event startet in 15 Sekunden");

        for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) p.sendTitle("§l§eEvent", "§g" + eventType.displayName);

        PluginMain.getInstance().getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {

                PluginMain.getInstance().brodcastMessage("§cDas Event §e" + eventType.getDisplayName() + " §cist nun §4aktiv§c!");

                if (eventType.equals(EventType.GOLDEN_APPLE)) {
                    List<Player> spectators = new ArrayList<>();
                    for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) if (PlayerManager.isRegisteredPlayer(p)) spectators.add(p);
                    List<Player> players = new ArrayList<>(PluginMain.getInstance().getServer().getOnlinePlayers().values());
                    players.removeAll(spectators);
                    Player target = players.get(new Random().nextInt(players.size()));
                    for (Player p : players) p.sendTitle("§l§7Du bekommst", "§cnichts");
                    if (target.getInventory().canAddItem(Item.get(ItemID.GOLDEN_APPLE))) {
                        target.sendTitle("§l§7Du bekommst", "§eEinen Goldapfel");
                        target.getInventory().addItem(Item.get(ItemID.GOLDEN_APPLE));
                    } else {
                        target.sendTitle("§l§7Du bekommst", "§cnichts (weil dein Inventar voll ist)");
                    }
                } else if (eventType.equals(EventType.BLINDNESS)) {
                    for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                        InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());
                        if (ip != null) {
                            if (!PlayerManager.isRegisteredPlayer(p)) return;
                            if (ip.getRank().equals(Rank.PLAYER)) {
                                p.addEffect(Effect.getEffect(Effect.BLINDNESS).setDuration(100000).setVisible(false).setAmplifier(1));
                            }
                        }
                    }
                } else if (eventType.equals(EventType.SHOW_NEXT_ENEMY)) {
                    for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                        int distance = (int)PluginMain.getInstance().calculateDistance(p.getLocation().east(), PluginMain.getInstance().findNearestPlayer(p).getLocation().east());
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§gEntfernung zum nächsten Spieler");
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§8-> §l§c" + distance + " §r§7Blöcke");
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§gEntfernung zum nächsten Spieler");
                        p.sendTitle("§gNächster Gegner:", "§l§c" + distance + " §r§7Blöcke");
                    }
                } else if (eventType.equals(EventType.INVENTORY_SWAP)) {
                    for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                        if (!PlayerManager.isRegisteredPlayer(p)) return;
                        p.getInventory().setContents(PlayerManager.swapInventoryContents(p.getInventory().getContents()));
                    }
                }

                if (eventType.isTemporaryEvent()) {
                    PluginMain.getInstance().activatedEvents.add(eventType);
                }
            }
        }, 300);

    }

    public static void stopEvent(EventType eventType) {
        PluginMain.getInstance().activatedEvents.remove(eventType);
        PluginMain.getInstance().brodcastMessage("§cDas Event §e" + eventType.getDisplayName() + " §cist nun beendet!");
        if (eventType.equals(EventType.POISON_RAIN)) {
            for (Level l : PluginMain.getInstance().getServer().getLevels().values()) l.setRaining(false);
        } else if (eventType.equals(EventType.BLINDNESS)) {
            for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) p.removeEffect(Effect.getEffect(Effect.BLINDNESS).getId());
        }
    }
}

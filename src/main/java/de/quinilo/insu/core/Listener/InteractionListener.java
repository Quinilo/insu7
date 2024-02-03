package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.CoordLeak;
import de.quinilo.insu.core.Utils.Forms;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.event.EventManager;
import de.quinilo.insu.core.event.EventType;

import java.text.Normalizer;

public class InteractionListener implements Listener {

    @EventHandler
    public void onFormWindowResponse(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();

        if (event.getWindow() instanceof FormWindowModal) {
            FormWindowModal window = (FormWindowModal) event.getWindow();

            for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                if (window.getResponse().getClickedButtonText().contains(p.getName())) {
                    player.teleport(p.getLocation());
                }
            }

        }

        if (event.getWindow() instanceof FormWindowCustom) {
            if (event.wasClosed()) return;

            if (event.getFormID() == 893531) {
                FormWindowCustom window = (FormWindowCustom) event.getWindow();
                String input = window.getResponse().getInputResponse(1);
                InsuPlayer ip = PlayerManager.getInsuPlayer(input);
                String punishment = "unknown";

                if (ip == null) {
                    player.sendMessage(PluginMain.getInstance().getPrefix() + "§cDer Spieler §e" + input + " §cist kein regestrierter Tilnehemr");
                    return;
                }

                ip.setStrikeLevel(ip.getStrikeLevel() + 1);

                Player target = PluginMain.getInstance().getServer().getPlayer(input);

                if (target == null) {
                    player.sendMessage(PluginMain.getInstance().getPrefix() + "§cDer Spieler §e" + input + " §cist nicht online");
                    return;
                }

                if (ip.getStrikeLevel() == 1) {
                    punishment = "Coordinaten leak";

                    CoordLeak leak = new CoordLeak(target.getName(), (int)target.getX() + ", " + (int)target.getY() + ", " + (int)target.getZ(), PluginMain.getInstance().getCurrentDate() + " (" + PluginMain.getInstance().getCurrentTime() + ")");
                    PluginMain.getInstance().getInsuData().coordLeaks.add(leak);

                    PluginMain.getInstance().brodcastMessage("§l§cCoordinaten-Leak");
                    PluginMain.getInstance().brodcastMessage("§7Die aktuellen Coorordinaten von §e" + leak.getPlayerName() + "§7 lauten §e" + leak.getCoordinate());
                    PluginMain.getInstance().brodcastMessage("§7-> Du kannst alle Leaks jeder Zeit mit §e/insu §7einsehen");
                    PluginMain.getInstance().brodcastMessage("§l§cCoordinaten-Leak");

                    PluginMain.getInstance().getDataConfig().saveData();
                } else if (ip.getStrikeLevel() == 2) {
                    punishment = "Inventar clear";

                    target.getInventory().clearAll();
                } else if (ip.getStrikeLevel() == 3) {
                    punishment = "Projektban";

                    PlayerManager.getInsuPlayer(target.getName()).setDead(true);
                    PluginMain.getInstance().getDataConfig().saveData();
                    player.kick("§cWegen wiederholtem Regelverstoß wurdest du aus INSU ausgeschlossen!\n\n§eVielen dank für deine Teilnahme!", false);
                }

                player.sendMessage(PluginMain.getInstance().getPrefix() +
                        "§7Der Spieler §e" + ip.getName() + " §7wurde mit der Bestrafung '§c" + punishment + "§7' bestraft. Er hat nun ein Strike-Level von §e" + ip.getStrikeLevel());
            }
        }

        if (event.getWindow() instanceof FormWindowSimple) {
            if (event.getResponse() instanceof FormResponseSimple) {
                FormWindowSimple formWindowSimple = (FormWindowSimple) event.getWindow();
                FormResponseSimple formResponseSimple = (FormResponseSimple) event.getResponse();
                String btn = formResponseSimple.getClickedButton().getText();
                String title = formWindowSimple.getTitle();

                if (title.contains("GameMaster")) {
                    if (btn.equals("§l§cStrike hinzufügen")) {
                        player.showFormWindow(Forms.addStrikeUI(), 893531);
                    } else if (btn.equals("§l§eEvents")) {
                        player.showFormWindow(Forms.eventsUI());
                    }
                } else if (title.equals(Forms.insuUI().getTitle())) {
                    if (btn.equals("§l§eTeamübersicht")) {
                        player.showFormWindow(Forms.teamLeaderboardUI());
                    } else if (btn.equals("§l§cCoord-Leaks")) {
                        player.showFormWindow(Forms.coordLeaksUI());
                    }
                } else if (title.equals(Forms.eventsUI().getTitle())) {
                    for (EventType eventType : EventType.values()) {
                        if (btn.contains(eventType.getDisplayName())) {
                            if (PluginMain.getInstance().getActivatedEvents().contains(eventType)) {
                                EventManager.stopEvent(eventType);
                            } else {
                                EventManager.executeEvent(eventType);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteractWithHider(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();

        if (item.getCustomName().equalsIgnoreCase("§8» §l§cTeleporter")) {
            player.showFormWindow(Forms.teleporterUI());
        }
    }
}

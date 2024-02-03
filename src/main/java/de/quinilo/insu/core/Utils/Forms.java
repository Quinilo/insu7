package de.quinilo.insu.core.Utils;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.event.EventType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Forms {

    public static FormWindowSimple gameMasterUI() {
        FormWindowSimple ui = new FormWindowSimple("§8» §l§cGameMaster", "§7Wähle eine Option");
        ui.addButton(new ElementButton("§l§eEvents", new ElementButtonImageData("path", "textures/ui/icon_blackfriday.png")));
        ui.addButton(new ElementButton("§l§cStrike hinzufügen", new ElementButtonImageData("path", "textures/ui/icon_book_writable.png")));
        ui.addButton(new ElementButton("§l§2Hauptmenu", new ElementButtonImageData("path", "textures/ui/icon_import.png")));
        return ui;
    }

    public static FormWindowCustom addStrikeUI() {
        FormWindowCustom ui = new FormWindowCustom("§8» §l§cStrike hinzufügen");
        ui.addElement(new ElementLabel("§7In dem du einem Spieler einen Strike hinzufügst werden beim:\n" +
                "1. mal seine Coordinaten geleakt\n" +
                "2. mal das Inventar gecleart\n" +
                "3. mal vom Projekt ausgeschlossen"));
        ui.addElement(new ElementInput("Spielername:", "..."));
        return ui;
    }

    public static FormWindowSimple insuUI() {
        FormWindowSimple ui = new FormWindowSimple(PluginMain.getInstance().getPrefix(), "§7Wähle eine Option");
        ui.addButton(new ElementButton("§l§eTeamübersicht", new ElementButtonImageData("path", "textures/ui/dressing_room_skins.png")));
        ui.addButton(new ElementButton("§l§cCoord-Leaks", new ElementButtonImageData("path", "textures/ui/icon_map.png")));
        return ui;
    }

    public static FormWindowSimple teamLeaderboardUI() {
        FormWindowSimple ui = new FormWindowSimple("§8» §l§eTamübersicht", "§cerror");
        StringBuilder builder = new StringBuilder();
        builder.append("§aGrün §7= am Leben, §eGelb §7= nicht regestriert, §cRot §7= tot oder disqualifiziert\n\n");
        for (Team team : PluginMain.getInstance().getInsuData().teams) {
            String teamColor = "§a";
            if (!team.name.equals("staffTeam")) {
                if (team.players.size() != 0) {

                    for (InsuPlayer p : team.players) if (p.getUuid() == null) teamColor = "§e";
                    if (team.players.stream().allMatch(kek -> kek.dead)) teamColor = "§c";

                    builder.append("§8» ");
                    builder.append(teamColor)
                            .append(team.getName())
                            .append(" §7-");

                    if (team.players.size() == 1) {
                        builder.append(" §bSolo");
                    } else {
                        for (InsuPlayer ip : team.players) {
                            String playerColor = "§a";
                            if (ip.getUuid() == null) playerColor = "§e";
                            if (ip.isDead()) playerColor = "§c";
                            builder.append(playerColor)
                                    .append(" ")
                                    .append(ip.getName());
                        }
                    }
                    builder.append("\n");
                }
            }
        }
        ui.setContent(builder.toString());
        return ui;
    }

    public static FormWindowSimple coordLeaksUI() {
        FormWindowSimple ui = new FormWindowSimple("§8» §l§cCoorordinaten-Leaks", "§cZurzeit gibt es noch keine Leaks.\n§aGlück gehabt!");
        InsuData insuData = PluginMain.getInstance().getInsuData();
        if (insuData.coordLeaks.size() != 0) {
            StringBuilder builder = new StringBuilder();
            builder.append("§7Zuzeit sind §e").append(insuData.coordLeaks.size()).append(" §7bekannt\n\n\n");

            for (CoordLeak leak : insuData.coordLeaks) {
                builder.append("§g")
                        .append(leak.getPlayerName())
                        .append(" - ")
                        .append(leak.date)
                        .append("\n")
                        .append("§e-> ")
                        .append(leak.coordinate)
                        .append("\n\n");
            }

            ui.setContent(builder.toString());
        }
        return ui;
    }

    public static FormWindowSimple eventsUI() {
        FormWindowSimple ui = new FormWindowSimple("§8» §l§eEvents", "§7Hier siehst du alle Events");
        for (EventType eventType : EventType.values()) {
            String substring = "Auslösen";
            if (eventType.isTemporaryEvent()) substring = "Aktivieren";
            if (PluginMain.getInstance().getActivatedEvents().contains(eventType)) substring = "§cDeaktivieren";
            ui.addButton(new ElementButton("§l§e" + eventType.getDisplayName() + "§r\n§a" + substring));
        }
        return ui;
    }

    public static FormWindowSimple teleporterUI() {
        FormWindowSimple ui = new FormWindowSimple("§8» §l§cTeleporter", "§7Wähle einen Spieler aus");

        List<Player> onlineSpieler = new ArrayList<>(PluginMain.getInstance().getServer().getOnlinePlayers().values());

        // Spieler nach Anzahl der Herzen sortieren
        Collections.sort(onlineSpieler, Comparator.comparingDouble(Player::getHealth));

        for (Player andererSpieler : onlineSpieler) {
            double herzen = andererSpieler.getHealth();
            int herzenInGanzenZahlen = (int) Math.ceil(herzen);
            ui.addButton(new ElementButton(andererSpieler.getName() + "\n" + herzenInGanzenZahlen));
        }
        return ui;
    }
}
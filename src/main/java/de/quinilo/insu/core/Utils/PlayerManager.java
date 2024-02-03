package de.quinilo.insu.core.Utils;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.level.Location;
import de.quinilo.insu.core.PluginMain;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class PlayerManager {

    @Getter
    public List<InsuPlayer> onlinePlayers = new ArrayList<>();
    @Getter @Setter
    private static PlayerManager instance;

    public static InsuPlayer getInsuPlayer(String name) {
        for (Team t : PluginMain.getInstance().getInsuData().teams) {
            for (InsuPlayer ip : t.players) {
                if (ip.getName().equals(name)) {
                    return ip;
                }
            }
        }
        return null;
    }

    public static Team getTeam(String name) {
        for (Team t : PluginMain.getInstance().getInsuData().teams) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    public static boolean isRegisteredPlayer(Player p) {
        InsuPlayer ip = getInsuPlayer(p.getName());
        return ip != null;
    }

    public static void addSpectator(Player p) {
        p.setGamemode(3);
        p.sendMessage(PluginMain.getInstance().getPrefix() + "§7Du bist nun ein Zuschauer");
    }

    //Bitte einfach wegschauen:
    public static Map<Integer, Item> swapInventoryContents(Map<Integer, Item> contents) {
        List<Integer> ints = new ArrayList<>();
        Map<Integer, Item> contentsBackup = contents;
        List<Item> items = new ArrayList<>();
        for (Map.Entry<Integer, Item> list : contentsBackup.entrySet()) items.add(list.getValue());
        for (Integer integer : contentsBackup.keySet()) {
            ints.add(integer);
        }

        contents = new HashMap<>();
        for (Map.Entry<Integer, Item> list : contentsBackup.entrySet()) {
            int random = ints.get(new Random().nextInt(ints.size()));
            Item randomItem = items.get(new Random().nextInt(items.size()));
            System.out.println(ints);
            System.out.println(random);
            ints.remove((Object)random);
            items.remove(randomItem);
            contents.put(random, randomItem);
        }
        return contents;
    }

    public static void checkAllRegisteredPlayers() {
        PluginMain.getInstance().log("Checking all Players");
        for (Team t : PluginMain.getInstance().getInsuData().teams) {
            for (InsuPlayer ip : t.players) {
                /*if (PluginMain.getInstance().isDateOutdated(ip.getLastLogin())) {
                    ip.setDead(true);
                    PluginMain.getInstance().log("§e" + ip.getName() + " §4wurde disqualifiziert");
                }*/
                if (ip.getLastLogin().equals("kekw")) {
                    ip.setDead(true);
                    PluginMain.getInstance().log("§e" + ip.getName() + " §4wurde disqualifiziert");
                }
            }
        }
        PluginMain.getInstance().getDataConfig().saveData();
    }
}

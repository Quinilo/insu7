package de.quinilo.insu.core.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

import java.util.ArrayList;
import java.util.List;

public class CloseServerTask extends Task {

    boolean firstSend = false;
    boolean secondSend = false;
    boolean thirdSend = false;

    public void onRun(int i) {

        if (!PluginMain.getInstance().getCurrentTime().split(":")[0].equals(PluginMain.getInstance().getStartTime()) || !(Integer.parseInt(PluginMain.getInstance().getCurrentTime().split(":")[1]) <= 29)) {
            for (Player p : PluginMain.getInstance().getServer().getOnlinePlayers().values()) {
                InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());
                if (ip != null && !ip.getTeam().equals("staffTeam")) {
                    p.kick("§4Die Spielzeit ist nun vorbei\n" +
                            "§cDu kannst wieder um §e" + PluginMain.getInstance().getStartTime() + " Uhr §closlegen\n" +
                            "§aVielen dank für deine bisherige Teilnahme", false);
                }
            }
            firstSend = false;
            secondSend = false;
            thirdSend = false;
            return;
        }
        if (PluginMain.getInstance().getCurrentTime().split(":")[1].equals("20")) {
            if (!firstSend) {
                PluginMain.getInstance().brodcastMessage("§cDer Server schließt in §e10 §cMinuten");
                firstSend = true;
            }
        }
        if (PluginMain.getInstance().getCurrentTime().split(":")[1].equals("25")) {
            if (!secondSend) {
                PluginMain.getInstance().brodcastMessage("§cDer Server schließt in §e5 §cMinuten");
                secondSend = true;
            }
        }
        if (PluginMain.getInstance().getCurrentTime().split(":")[1].equals("29")) {
            if (!thirdSend) {
                PluginMain.getInstance().brodcastMessage("§cDer Server schließt in §e1 §cMinute");
                thirdSend = true;
            }
        }
    }
}

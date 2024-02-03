package de.quinilo.insu.core.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.scheduler.Task;
import java.util.Collection;
import java.util.Map;

public class LifeDisplay
extends Task {
    public void onRun(int i) {
        for (Player p : Server.getInstance().getOnlinePlayers().values()) {
            p.setScoreTag("" + p.getHealth() + " \u00a7c\u2764");
        }
    }
}


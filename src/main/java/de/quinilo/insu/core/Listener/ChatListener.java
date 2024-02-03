package de.quinilo.insu.core.Listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import de.quinilo.insu.core.Utils.InsuData;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String msg = event.getMessage();
        InsuPlayer ip = PlayerManager.getInsuPlayer(player.getName());

        if (ip == null) {
            event.setFormat("§8» §l§6Zuschauer §r§8| §l§e" + player.getDisplayName() + " §r\u00a78\u00bb \u00a7f" + msg);
        } else {
            event.setFormat("§8» §l§6" + ip.getTeam() + " §r§8| §l§e" + player.getDisplayName() + " §r\u00a78\u00bb \u00a7f" + msg);
        }
    }
}


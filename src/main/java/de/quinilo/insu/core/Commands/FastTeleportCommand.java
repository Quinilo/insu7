package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

import java.util.Objects;

public class FastTeleportCommand extends PluginCommand<PluginMain> {

    public FastTeleportCommand(PluginMain owner) {
        super("ttp", owner);
        this.setDescription("");
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp() || Objects.requireNonNull(PlayerManager.getInsuPlayer(p.getName())).getRank().equals(Rank.GAME_MASTER)) {
                if (args.length == 1) {
                    Player target = PluginMain.getInstance().getServer().getPlayer(args[0]);
                    if (target != null) {
                        p.teleport(target.getLocation());
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§7Du hast dich zu §e" + target.getName() + " §7teleportiert");
                    } else {
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§cDieser Spieler ist nicht online");
                    }
                } else {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§cBitte gebe einen Spieler an");
                }
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

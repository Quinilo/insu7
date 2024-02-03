package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuData;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

public class ReviveCommand extends PluginCommand<PluginMain> {

    public ReviveCommand(PluginMain owner) {
        super("revive", owner);
        this.setDescription("");
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.STRING, false),
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                if (args.length == 1) {
                    InsuPlayer ip = PlayerManager.getInsuPlayer(args[0]);
                    if (ip != null) {
                        ip.setDead(false);
                        PluginMain.getInstance().getDataConfig().saveData();
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§aDu hast §e" + args[0] + " §awiederbelebt");
                    } else {
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§cDer Spieler §e" + args[0] + " §cexestiert nicht");
                    }
                } else {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§cBitte gebe einen Namen an");
                }
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else {
            if (args.length == 1) {
                InsuPlayer ip = PlayerManager.getInsuPlayer(args[0]);
                if (ip != null) {
                    ip.setDead(false);
                    PluginMain.getInstance().getDataConfig().saveData();
                    commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§aDu hast §e" + args[0] + " §awiederbelebt");
                    if (ip.getRank().equals(Rank.GAME_MASTER)) {
                        ip.setLastLogin("hanswurst");
                        PluginMain.getInstance().getDataConfig().saveData();
                    }
                } else {
                    commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§cDer Spieler §e" + args[0] + " §cexestiert nicht");
                }
            } else {
                commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§cBitte gebe einen Namen an");
            }
        }

        return false;
    }
}

package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Team;

import java.util.ArrayList;

public class SetTeamSpawnCommand extends PluginCommand<PluginMain> {

    public SetTeamSpawnCommand(PluginMain owner) {
        super("setteamspawn", owner);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("teamname", CommandParamType.STRING, false),
        });
        this.setDescription("Setteamspawn");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp())  {
                if (args.length == 1) {
                    Team t = PlayerManager.getTeam(args[0]);
                    if (t == null) {
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§cDas Team §e" + args[0] + " §cexistiert nicht");
                    } else {
                        t.setX(p.getLocation().asBlockVector3().getX());
                        t.setY(p.getLocation().asBlockVector3().getY());
                        t.setZ(p.getLocation().asBlockVector3().getZ());
                        PluginMain.getInstance().getDataConfig().saveData();
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§aDer Teamspawn von §e" + args[0] + " §awurde auf deine aktuelle position gesetzt");
                    }
                } else {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§cBite gebe einen Team Namen an");
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

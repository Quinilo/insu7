package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.Team;

import java.util.ArrayList;

public class RegisterTeamCommand extends PluginCommand<PluginMain> {

    public RegisterTeamCommand(PluginMain owner) {
        super("registerteam", owner);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("name", CommandParamType.STRING, false),
        });
        this.setDescription("Registerteam");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                if (args.length == 1) {
                    PluginMain.getInstance().getInsuData().teams.add(new Team(new ArrayList<>(), args[0], false));
                    PluginMain.getInstance().getDataConfig().saveData();
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "Das Team §a" + args[0] + " §7wurde erolgreich hinzugefügt");
                } else {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§cBite gebe einen Namen an");
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

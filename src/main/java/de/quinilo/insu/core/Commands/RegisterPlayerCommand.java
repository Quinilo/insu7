package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;
import de.quinilo.insu.core.Utils.Team;

public class RegisterPlayerCommand extends PluginCommand<PluginMain> {

    public RegisterPlayerCommand(PluginMain owner) {
        super("registerplayer", owner);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("name", CommandParamType.STRING, false),
                new CommandParameter("team", CommandParamType.STRING, false),
        });
        this.setDescription("Registerteam");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                if (args.length == 2) {
                    Team t = PlayerManager.getTeam(args[1]);
                    if (t == null) {
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "§cDas Team §e" + args[1] + " §cexistiert nicht");
                    } else {
                        t.players.add(new InsuPlayer(args[0], null, false, t.getName(), Rank.PLAYER));
                        PluginMain.getInstance().getDataConfig().saveData();
                        p.sendMessage(PluginMain.getInstance().getPrefix() + "Der Spieler §a" + args[0] + " §7wurde erolgreich hinzugefügt");
                    }
                } else {
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "§cBite gebe einen Namen und ein Team an");
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

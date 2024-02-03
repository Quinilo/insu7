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

public class RegisterGameMasterCommand extends PluginCommand<PluginMain> {

    public RegisterGameMasterCommand(PluginMain owner) {
        super("registergamemaster", owner);
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("name", CommandParamType.STRING, false),
        });
        this.setDescription("");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                if (args.length == 1) {
                    Team t = PlayerManager.getTeam("staffTeam");
                    t.players.add(new InsuPlayer(args[0], null, false, t.getName(), Rank.GAME_MASTER));
                    PluginMain.getInstance().getDataConfig().saveData();
                    p.sendMessage(PluginMain.getInstance().getPrefix() + "Der Spieler §a" + args[0] + " §7wurde als §cGameMaster §7regestriert");
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

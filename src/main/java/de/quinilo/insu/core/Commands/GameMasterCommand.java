package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.*;

import java.util.ArrayList;

public class GameMasterCommand extends PluginCommand<PluginMain> {

    public GameMasterCommand(PluginMain owner) {
        super("gamemaster", owner);
        this.setDescription("Registerteam");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());
            if (p.isOp() || ip != null && ip.getRank().equals(Rank.GAME_MASTER) || ip != null && ip.getRank().equals(Rank.ADMIN)) {
                p.showFormWindow(Forms.gameMasterUI());
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

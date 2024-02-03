package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.Team;

import java.util.ArrayList;

public class SaveConfigCommand extends PluginCommand<PluginMain> {

    public SaveConfigCommand(PluginMain owner) {
        super("saveconfig", owner);
        this.setDescription("Saveconfig");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                PluginMain.getInstance().getDataConfig().saveData();
                PluginMain.getInstance().getDataConfig().saveStartTime();
                p.sendMessage(PluginMain.getInstance().getPrefix() + "§aDie Config wurde erfolgreich gespeichert");
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

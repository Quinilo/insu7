package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import de.quinilo.insu.core.PluginMain;

public class ShowDataCommand extends PluginCommand<PluginMain> {

    public ShowDataCommand(PluginMain owner) {
        super("showdata", owner);
        this.setDescription("");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                p.sendMessage(PluginMain.getInstance().getDataConfig().getData());
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

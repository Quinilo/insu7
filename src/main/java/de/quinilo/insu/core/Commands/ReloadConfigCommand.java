package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import de.quinilo.insu.core.PluginMain;

public class ReloadConfigCommand extends PluginCommand<PluginMain> {

    public ReloadConfigCommand(PluginMain owner) {
        super("reloadconfig", owner);
        this.setDescription("reloadconfig");
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

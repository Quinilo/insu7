package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.Forms;
import de.quinilo.insu.core.Utils.InsuPlayer;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.Utils.Rank;

public class InsuCommand extends PluginCommand<PluginMain> {

    public InsuCommand(PluginMain owner) {
        super("insu", owner);
        this.setDescription("§2INSU Menu");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            InsuPlayer ip = PlayerManager.getInsuPlayer(p.getName());
            p.showFormWindow(Forms.insuUI());
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

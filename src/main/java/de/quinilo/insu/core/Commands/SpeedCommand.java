package de.quinilo.insu.core.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.potion.Effect;
import de.quinilo.insu.core.PluginMain;
import org.apache.logging.log4j.core.config.plugins.processor.PluginCache;

public class SpeedCommand extends PluginCommand<PluginMain> {

    public SpeedCommand(PluginMain owner) {
        super("speed", owner);
        this.setDescription("");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] args ) {
        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.isOp()) {
                p.addEffect(Effect.getEffect(1).setDuration(5000).setVisible(false).setAmplifier(20));
            } else {
                p.sendMessage(PluginMain.getInstance().getNoPermsMessage());
            }
        }else{
            commandSender.sendMessage(PluginMain.getInstance().getPrefix() + "§4Nur Spieler können diesen Command ausführen!");
        }

        return false;
    }
}

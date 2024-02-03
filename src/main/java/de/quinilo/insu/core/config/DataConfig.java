package de.quinilo.insu.core.config;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import de.quinilo.insu.core.PluginMain;
import de.quinilo.insu.core.Utils.InsuData;
import de.quinilo.insu.core.Utils.Team;

import java.io.File;
import java.util.ArrayList;

public class DataConfig {

    private PluginMain plugin;
    private Config config;
    private File file;
    private static String currentMapFile;

    public int spawn1;

    public DataConfig() {
        this.file = new File( PluginMain.getInstance().getDataFolder(), "config.yml");
        this.config = new cn.nukkit.utils.Config( this.file, cn.nukkit.utils.Config.YAML);
        createDefaultConfig();
    }

    public void createDefaultConfig() {
        this.addDefault("startTime", "18");
        this.addDefault("startDate", "12.08.2023");
        this.addDefault("data", "null");
    }

    public void readConfig() {
        if (config.getString("data").equals("null")) {
            PluginMain.getInstance().setInsuData(new InsuData());
            PluginMain.getInstance().getInsuData().teams.add(new Team(new ArrayList<>(), "staffTeam", false));
            PluginMain.getInstance().log("§ccreating new Data");
        } else {
            PluginMain.getInstance().setInsuData(PluginMain.getInstance().readData(config.getString("data")));
            PluginMain.getInstance().log("§aData loaded");
        }
        PluginMain.getInstance().setStartTime(config.getString("startTime"));
        PluginMain.getInstance().setStartDate(config.getString("startDate"));
    }

    public String getData() {
        return this.config.getString("data");
    }

    public void saveData() {
        config.set("data", PluginMain.getInstance().translateData());
        config.save();
    }

    public void saveStartTime() {
        config.set("startTime", PluginMain.getInstance().getStartTime());
        config.save();
    }

    public void saveStartDate() {
        config.set("startDate", PluginMain.getInstance().getStartTime());
        config.save();
    }

    public void addDefault(String path, Object object) {
        if (!this.config.exists( path )) {
            this.config.set( path, object );
            this.config.save( this.file );
        }
    }
}

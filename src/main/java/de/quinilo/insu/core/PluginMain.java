package de.quinilo.insu.core;

import cn.nukkit.Player;
import cn.nukkit.command.SimpleCommandMap;
import cn.nukkit.event.Listener;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.scheduler.ServerScheduler;
import com.google.gson.Gson;
import de.quinilo.insu.core.Commands.*;
import de.quinilo.insu.core.Listener.*;
import de.quinilo.insu.core.Tasks.CloseServerTask;
import de.quinilo.insu.core.Tasks.PoisonRainTask;
import de.quinilo.insu.core.Utils.InsuData;
import de.quinilo.insu.core.Utils.PlayerManager;
import de.quinilo.insu.core.config.DataConfig;
import de.quinilo.insu.core.event.EventType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class PluginMain extends PluginBase {

    @Getter
    String prefix = "§8» §l§6In§esu §c7 §r§8| §7";

    @Getter
    String noPermsMessage = prefix + "§cNetter Versuch xD";

    @Getter
    private static PluginMain instance;

    @Getter @Setter
    InsuData insuData;
    Gson gson;

    @Getter
    DataConfig dataConfig;

    @Getter @Setter
    String startTime = "21";

    @Getter @Setter
    String startDate = "09.09.1999";

    @Getter
    public List<EventType> activatedEvents = new ArrayList<>();
    @Getter
    public HashMap<Player, String> customDeathMessages = new HashMap<>();

    @Override
    public void onLoad() {
        log("§6loading data");

        instance = this;
        insuData = new InsuData();
        gson = new Gson();
        dataConfig = new DataConfig();
        dataConfig.readConfig();

        PlayerManager.setInstance(new PlayerManager());
    }

    public void onEnable() {
        logImportant("§6loading plugin...");
        this.registerListener();
        this.registerTasks();
        this.registerCommands();

        PlayerManager.checkAllRegisteredPlayers();

        logImportant("§aenabled!");
        log("§8-> §2PLUGIN BY QUINILO");
        log("");
    }

    public void onDisable() {
        logImportant("§cdisabled!");
    }

    public void logImportant(String msg) {
        this.getLogger().info("                  ");
        this.getLogger().info("                  ");
        this.getLogger().info(this.prefix + msg);
        this.getLogger().info("                  ");
        this.getLogger().info("                  ");
    }

    public void log(String msg) {
        this.getLogger().info(msg);
    }

    public void registerListener() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new DeathListener(), this);
        pluginManager.registerEvents(new MoveListener(this), this);
        pluginManager.registerEvents(new ConnectionListener(), this);
        pluginManager.registerEvents(new InteractionListener(), this);
        pluginManager.registerEvents(new DamageListener(), this);
        pluginManager.registerEvents(new BlockListener(), this);
        log("Registered Listeners");
    }

    public void registerTasks() {
        ServerScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleRepeatingTask(new CloseServerTask(), 20);
        scheduler.scheduleRepeatingTask(new PoisonRainTask(), 30);
        log("Registered Tasks");
    }

    public void registerCommands() {
        SimpleCommandMap commandmap = this.getServer().getCommandMap();
        commandmap.register("command", new RegisterTeamCommand(this));
        commandmap.register("command", new RegisterPlayerCommand(this));
        commandmap.register("command", new SaveConfigCommand(this));
        commandmap.register("command", new SetTeamSpawnCommand(this));
        commandmap.register("command", new GameMasterCommand(this));
        commandmap.register("command", new InsuCommand(this));
        commandmap.register("command", new RegisterGameMasterCommand(this));
        commandmap.register("command", new ShowDataCommand(this));
        commandmap.register("command", new FastTeleportCommand(this));
        commandmap.register("command", new ReviveCommand(this));
        commandmap.register("command", new SpeedCommand(this));
        log("Registered Commands");
    }

    public String translateData() {
        return gson.toJson(insuData);
    }

    public void brodcastMessage(String msg) {
        for (Player p : getServer().getOnlinePlayers().values()) p.sendMessage(prefix + msg);
    }

    public InsuData readData(String string) {
        return gson.fromJson(string, insuData.getClass());
    }

    public String getCurrentTime() {
        LocalDateTime t = LocalDateTime.now(ZoneId.of("Europe/Berlin"));
        return t.getHour() + ":" + t.getMinute();
    }

    public String getCurrentDate() {
        LocalDateTime t = LocalDateTime.now(ZoneId.of("Europe/Berlin"));
        return t.getDayOfMonth() + "." + t.getMonthValue() + "." + t.getYear();
    }

    public String getCurrentDayString(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate startDate = LocalDate.parse(inputDate, formatter);
        LocalDate currentDate = LocalDate.now();

        long daysDifference = ChronoUnit.DAYS.between(startDate, currentDate);

        if (daysDifference > 0) {
            return "§l§6Tag " + daysDifference;
        } else if (daysDifference < 0) {
            return "§l§6" + Math.abs(daysDifference) + " Tage vor Start";
        } else {
            return "§l§aProjektstart\n§r§2" + getCurrentDate();
        }
    }

    public double calculateDistance(Vector3 v1, Vector3 v2) {
        double dx = v1.x - v2.x;
        double dy = v1.y - v2.y;
        double dz = v1.z - v2.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public Player findNearestPlayer(Player sourcePlayer) {
        Player nearestPlayer = null;
        double minDistanceSquared = Double.MAX_VALUE;

        for (Player player : getServer().getOnlinePlayers().values()) {
            if (player.equals(sourcePlayer) || PlayerManager.getInsuPlayer(player.getName()) == null || Objects.requireNonNull(PlayerManager.getInsuPlayer(player.getName())).getTeam().equals("staffTeam")) {
                continue;
            }

            if (PlayerManager.getTeam(PlayerManager.getInsuPlayer(sourcePlayer.getName()).getTeam()).players.contains(player)) continue;

            double distanceSquared = player.getLocation().distanceSquared(sourcePlayer.getLocation());
            if (distanceSquared < minDistanceSquared) {
                nearestPlayer = player;
                minDistanceSquared = distanceSquared;
            }
        }

        return nearestPlayer;
    }

    public boolean isDateOutdated(String date) {
        if (date.equals("kekw")) return false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate lastLogin = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();

        long daysDifference = ChronoUnit.DAYS.between(lastLogin, currentDate);

        return daysDifference > 4;
    }

    public static <K, V> V getRandomValueFromMap(Map<K, V> map) {
        if (map.isEmpty()) {
            return null;
        }
        
        List<V> valueList = new ArrayList<>(map.values());
        Random random = new Random();
        int randomIndex = random.nextInt(valueList.size());

        return valueList.get(randomIndex);
    }
}


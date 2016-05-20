package xyz.saboteur.spigot;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.saboteur.spigot.cmd.CmdPvPTimer;
import xyz.saboteur.spigot.listeners.PvPListener;
import xyz.saboteur.spigot.objects.TimerAPI;
import xyz.saboteur.spigot.util.PTL;

import java.io.File;

public class PvPTimer extends JavaPlugin {

    private static PvPTimer instance;

    public void onEnable() {
        if(!new File(getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
            PTL.writeToConfig(this.getConfig());
            saveConfig();
        }
        TimerAPI.get(); // Initializes
        getServer().getPluginManager().registerEvents(new PvPListener(), this);
        getCommand("pvptimer").setExecutor(new CmdPvPTimer(this));
    }

    public static PvPTimer get() {
        return instance == null ? instance = PvPTimer.getPlugin(PvPTimer.class) : instance;
    }

}

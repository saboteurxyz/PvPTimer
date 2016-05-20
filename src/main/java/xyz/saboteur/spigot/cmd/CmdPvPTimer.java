package xyz.saboteur.spigot.cmd;

import com.google.common.base.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import xyz.saboteur.spigot.PvPTimer;
import xyz.saboteur.spigot.objects.TimerAPI;

import static xyz.saboteur.spigot.util.PUtil.send;

public class CmdPvPTimer implements CommandExecutor {
    private PvPTimer plugin;
    private TimerAPI timerAPI;

    public CmdPvPTimer(PvPTimer plugin) {
        this.plugin = plugin;
        this.timerAPI = TimerAPI.get();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
            send(sender, "&9{separator}", "separator", Strings.repeat("-", 53));
            PluginDescriptionFile pdf = plugin.getDescription();
            send(sender, "&aSaboteur's PvPTimer-{version} Help:", "version", pdf.getVersion());
            send(sender, "&b/{label} {args} &8- &7{desc}", "label", label, "args", "[help]", "desc", "Shows this help message");
            send(sender, "&b/{label} {args} &8- &7{desc}", "label", label, "args", "check", "desc", "Check how much time you have on your timer");
            send(sender, "&b/{label} {args} &8- &7{desc}", "label", label, "args", "remove", "desc", "Remove your pvp immunity");
            send(sender, "&9{separator}", "separator", Strings.repeat("-", 53));
            return true;
        } else if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("check")) {
                    send(sender, timerAPI.getTimerString(player));
                    return true;
                } else if(args[0].equalsIgnoreCase("remove")) {
                    timerAPI.removeTimer(player);
                    return true;
                }
            }
        }
        send(sender, "&cCommand not recognized. Use /{label} for help.", "label", label);
        return true;
    }

}

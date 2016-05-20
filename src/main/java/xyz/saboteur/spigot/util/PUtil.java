package xyz.saboteur.spigot.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;

public class PUtil {

    private static DecimalFormat format = new DecimalFormat("00");

    public static String format(String s, Object... objects) {
        if(objects != null)
            for(int i = 0; i < objects.length; i+= 2)
                s = s.replaceAll("\\{" + objects[i] + "\\}", "" + objects[i+1]);
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void send(CommandSender sender, String msg) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void send(CommandSender sender, String s, Object... objects) {
        send(sender, format(s, objects));
    }

    // int totalSeconds = (int)(System.currentTimeMillis() - timeSince)/1000;
    public static String getTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int totalMinutes = totalSeconds / 60;
        int minutes = totalMinutes % 60;
        int hours = totalMinutes / 60;
        if(totalSeconds == 0) return "0:00";
        return (hours != 0 ? hours + ":" : "") + minutes + ":" + format.format(seconds);
    }
}

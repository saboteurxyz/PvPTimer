package xyz.saboteur.spigot.util;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.saboteur.spigot.PvPTimer;

public enum PTL {
    newPlayer("&e&l(!) &eYou're immune to pvp for {time} Type /timer remove to remove your pvp timer"),
    gotImmunity("&d&l(!) &dYou're immune for {time} ({reason}). Type /timer remove to remove your pvp timer"),
    lostImmunity("&c&l(!) You are no longer immune to pvp"),
    cantDamageThem("&c&l(!) You can't hurt {player} because they are immune to pvp!"),
    cantDamageYou("&c&l(!) You can't hurt {player} because you are immune to pvp! &eType /timer remove to remove your pvp timer"),
    checkImmunity("&a&l(!) IMMUNITY >> &a{time}."),
    checkImmunityNone("&c&l(!) >> NO IMMUNITY <<");

    private final String message;

    PTL(String message) {
        this.message = PvPTimer.get().getConfig().getString("messages." + name().replaceAll("_", "."), message);
    }

    public void send(CommandSender sender, Object... replaced) {
        PUtil.send(sender, message, replaced);
    }

    public String get(Object... replaced) {
        return PUtil.format(message, replaced);
    }

    @Override
    public String toString() {
        return message;
    }

    public static void writeToConfig(FileConfiguration config) {
        for(PTL tl : values())
            config.set("messages." + tl.name().replaceAll("_", ".").toLowerCase(), tl.toString());
    }
}

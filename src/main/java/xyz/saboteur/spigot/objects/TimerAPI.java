package xyz.saboteur.spigot.objects;

import org.bukkit.entity.Player;
import xyz.saboteur.spigot.util.PTL;
import xyz.saboteur.spigot.util.PUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TimerAPI {
    private static TimerAPI instance;
    private Map<UUID, Long> timers = new HashMap<>();

    public void grantTimer(Player player, ImmunityType type) {
        int time = type.getTime();
        if(time <= 0 || getTimeLeft(player) > time) return;
        PTL.gotImmunity.send(player, "time", PUtil.getTime(type.getTime()), "reason", type.getReadable());
        timers.put(player.getUniqueId(), System.currentTimeMillis() + time*1000);
    }

    public void removeTimer(Player player) {
        if(!hasTimer(player)) return;
        timers.remove(player.getUniqueId());
        PTL.lostImmunity.send(player, "player", player.getName());
    }

    public String getTimerString(Player player) {
        if(!hasTimer(player)) return PTL.checkImmunityNone.get();
        return PTL.checkImmunity.get("time", PUtil.getTime(getTimeLeft(player)));
    }

    public boolean hasTimer(Player player) {
        UUID uuid = player.getUniqueId();
        boolean has = timers.containsKey(uuid);
        if(has && (int)(timers.get(player.getUniqueId()) - System.currentTimeMillis())/1000 <= 0) {
            timers.remove(uuid);
            has = false;
        }
        return has;
    }

    public int getTimeLeft(Player player) {
        return hasTimer(player) ? (int)(timers.get(player.getUniqueId()) - System.currentTimeMillis())/1000 : 0;
    }

    public static TimerAPI get() {
        return instance == null ? instance = new TimerAPI() : instance;
    }

}

package xyz.saboteur.spigot.objects;

import org.apache.commons.lang.WordUtils;
import xyz.saboteur.spigot.PvPTimer;

public enum ImmunityType {
    new_Player, join, respawn, teleport, world_Changed;

    private int time;

    ImmunityType() {
        this.time = PvPTimer.get().getConfig().getInt(getConfigNode(), 0);
    }

    public String getReadable() {
        return WordUtils.capitalizeFully(name().replace('_', ' '));
    }

    public String getConfigNode() {
        return String.format("types.%s", name().replaceAll("_", ""));
    }

    public int getTime() {
        return time;
    }

}

package xyz.saboteur.spigot.listeners;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.projectiles.ProjectileSource;
import xyz.saboteur.spigot.objects.ImmunityType;
import xyz.saboteur.spigot.objects.TimerAPI;
import xyz.saboteur.spigot.util.PTL;

public class PvPListener implements Listener {

    private TimerAPI timerAPI;

    public PvPListener() {
        this.timerAPI = TimerAPI.get();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.timerAPI.grantTimer(event.getPlayer(), event.getPlayer().hasPlayedBefore() ? ImmunityType.join : ImmunityType.new_Player);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        this.timerAPI.grantTimer(event.getPlayer(), ImmunityType.respawn);
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        this.timerAPI.grantTimer(event.getPlayer(), ImmunityType.teleport);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        this.timerAPI.grantTimer(event.getPlayer(), ImmunityType.world_Changed);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamaged(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Entity entityDamager = event.getDamager();
        Player player = (Player) event.getEntity();
        Player damager = null;
        if(entityDamager instanceof Player) {
            damager = (Player) entityDamager;
        } else if(entityDamager instanceof Arrow) {
            ProjectileSource shooter = ((Arrow)entityDamager).getShooter();
            if(!(shooter instanceof Player)) return;
            damager = (Player) shooter;
        }
        if(timerAPI.hasTimer(damager)) {
            PTL.cantDamageYou.send(damager, "player", player.getName());
            return;
        }
        if(timerAPI.hasTimer(player))
            PTL.cantDamageThem.send(damager, "player", player.getName());
    }

}

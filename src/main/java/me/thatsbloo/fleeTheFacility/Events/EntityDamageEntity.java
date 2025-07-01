package me.thatsbloo.fleeTheFacility.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.thatsbloo.fleeTheFacility.FleeTheFacility;

public class EntityDamageEntity implements Listener {

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (!(e.getDamager() instanceof Player)) {
            return;
        }
        Player victim = (Player) e.getEntity();
        Player attacker = (Player) e.getDamager();
        if (FleeTheFacility.beastUUID == null) {
            return;
        }
        if (!FleeTheFacility.beastUUID.equals(attacker.getUniqueId())) {
            return;
        }
        System.out.println("Beast hit player: " + victim.getName() + " by " + attacker.getName());
    }
}

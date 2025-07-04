package me.thatsbloo.fleeTheFacility.Hacking;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import me.thatsbloo.fleeTheFacility.FleeTheFacility;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class HackListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            if (block != null && block.getType() == Material.ENCHANTING_TABLE) {
                HackManager hackManager = FleeTheFacility.getPlugin(FleeTheFacility.class).hackManager;
                if (event.getPlayer().isSneaking()) {
                    hackManager.attemptHack(player);
                } else {
                    hackManager.startHack(player);
                }

                event.setCancelled(true); // Prevents the default interaction with the enchanting table
            }
        }
    }
}
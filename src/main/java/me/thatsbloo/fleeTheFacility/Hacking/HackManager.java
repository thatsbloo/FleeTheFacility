package me.thatsbloo.fleeTheFacility.Hacking;


import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class HackManager {
    private final JavaPlugin plugin;
    private final HashMap<UUID, HackSession> activeHacks = new HashMap<>();

    public HackManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startHack(Player player) {
        if (activeHacks.containsKey(player.getUniqueId())) return;

        HackSession session = new HackSession(player);
        activeHacks.put(player.getUniqueId(), session);
        session.runTaskTimer(plugin, 0L, 2L); // 2 ticks = 100ms
    }

    public void attemptHack(Player player) {
        HackSession session = activeHacks.get(player.getUniqueId());
        if (session == null) return;

        session.attempt();
    }

    private class HackSession extends BukkitRunnable {
        private final Player player;
        private int tick = 0;
        private boolean attempted = false;

        private final int barLength = 20;
        private final int greenStart = 8;
        private final int greenEnd = 12;

        public HackSession(Player player) {
            this.player = player;
        }

        @Override
        public void run() {
            if (tick >= barLength || attempted) {
                cancel();

                if (!attempted) {
                    player.sendMessage("You failed to hack the terminal!");
                }

                activeHacks.remove(player.getUniqueId());
                return;
            }

            StringBuilder bar = new StringBuilder();
            for (int i = 0; i < barLength; i++) {
                if (i == tick) {
                    bar.append(ChatColor.WHITE).append("|"); // White for current tick
                }  else if (i >= greenStart && i <= greenEnd) {
                        bar.append(ChatColor.GREEN).append("█");
                } else {
                    bar.append(ChatColor.DARK_GRAY).append("█");
                }
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(bar.toString()));
            tick++;
        }

        public void attempt() {
            attempted = true;
            
            if (tick >= greenStart && tick <= greenEnd) {
                player.sendMessage("Hack successful!");
                // Trigger success logic here, e.g., open a door or give an item
            } else {
                player.sendMessage("Hack failed! You were too slow.");
            }
            
        }
    }
}
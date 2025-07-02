package me.thatsbloo.fleeTheFacility.Players;

import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.level.ServerPlayer;

public class RagdollEntity {
    
    public static void execute(Player player) {
        ServerPlayer entityPlayer = ((CraftPlayer) player).getHandle();
    }
}

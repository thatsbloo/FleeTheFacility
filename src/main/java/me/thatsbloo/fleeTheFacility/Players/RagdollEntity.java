package me.thatsbloo.fleeTheFacility.Players;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import io.papermc.paper.connection.PlayerConnection;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerEntity;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

public class RagdollEntity {

    private ServerPlayer ragdoll;
    private ServerLevel level;

    public RagdollEntity(Player player) {
        ServerPlayer craftPlayer = ((CraftPlayer) player).getHandle();

        this.level = ((CraftWorld)player.getWorld()).getHandle();

        //get textures of the player and set to npc
        Property textures = (Property) craftPlayer.getGameProfile().getProperties().get("textures").toArray()[0];
        UUID uuid;
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName());
        gameProfile.getProperties().put("textures", new Property("textures", textures.value(), textures.signature()));

        //our fake player model
        this.ragdoll = new FakePlayer(
                ((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld)player.getWorld()).getHandle(),
                gameProfile,
                craftPlayer.clientInformation()
        );


        this.ragdoll.setPos(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

        this.ragdoll.setPose(Pose.SLEEPING);

        this.broadcastExistenceToAll();
    }

    private ServerEntity getAsServerEntity() {
        //Mojang you are ugly and stupid
        return new ServerEntity(
                this.level,
                this.ragdoll,
                EntityType.PLAYER.updateInterval(),
                false,
                packet -> {},
                (packet, ignored) -> {},
                Set.of()
        );
    }
    public void broadcastExistenceToAll() {
        ServerEntity helper = this.getAsServerEntity();

        for (Player playerOnline : Bukkit.getOnlinePlayers()) {
            ServerGamePacketListenerImpl connection = ((CraftPlayer)playerOnline).getHandle().connection;
            // RAHHH LONG NAMES MOJANG MAPPING AHSDJK,NAK
            connection.send(new ClientboundPlayerInfoUpdatePacket(
                    ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
                    ragdoll
            ));

            connection.send(new ClientboundAddEntityPacket(ragdoll, helper));

            connection.send(new ClientboundSetEntityDataPacket(ragdoll.getId(), ragdoll.getEntityData().getNonDefaultValues()));

        }
    }

    public void broadcastExistenceToPlayer(Player player) {
        ServerEntity helper = this.getAsServerEntity();

        ServerGamePacketListenerImpl connection = ((CraftPlayer)player).getHandle().connection;
        // RAHHH LONG NAMES MOJANG MAPPING AHSDJK,NAK
        connection.send(new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER,
                ragdoll
        ));



        connection.send(new ClientboundAddEntityPacket(ragdoll, helper));

        connection.send(new ClientboundSetEntityDataPacket(ragdoll.getId(), ragdoll.getEntityData().getNonDefaultValues()));
    }

    public void broadcastStateToPlayers() {
        // I DONT LIKE YOU MOJANG
        ServerEntity helper = this.getAsServerEntity();

        for (Player playerOnline : Bukkit.getOnlinePlayers()) {
            ServerGamePacketListenerImpl connection = ((CraftPlayer)playerOnline).getHandle().connection;


            connection.send(new ClientboundSetEntityDataPacket(ragdoll.getId(), ragdoll.getEntityData().getNonDefaultValues()));

        }
    }


}

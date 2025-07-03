package me.thatsbloo.fleeTheFacility.Players;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ClientInformation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class FakePlayer extends ServerPlayer {

    public FakePlayer(MinecraftServer server, ServerLevel level, GameProfile gameProfile, ClientInformation clientInformation) {
        super(server, level, gameProfile, clientInformation);

        //fake connection type shit
        this.connection = new FakeConnection(this);
    }

    private static class FakeConnection extends ServerGamePacketListenerImpl {

        public FakeConnection(ServerPlayer player) {
            super(player.getServer(), new net.minecraft.network.Connection(PacketFlow.CLIENTBOUND), player, CommonListenerCookie.createInitial(player.gameProfile, true));
        }

        @Override
        public int latency() {
            return 0;
        }
    }
}


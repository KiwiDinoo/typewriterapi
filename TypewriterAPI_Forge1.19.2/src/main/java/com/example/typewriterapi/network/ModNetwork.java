package com.example.typewriterapi.network;

import com.example.typewriterapi.network.packet.DisplayTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetwork {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("typewriterapi", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void registerPackets() {
        CHANNEL.registerMessage(
                id++,
                DisplayTextPacket.class,
                DisplayTextPacket::encode,
                DisplayTextPacket::new,
                (packet, ctx) -> {
                    ctx.get().enqueueWork(packet::handle);
                    ctx.get().setPacketHandled(true);
                },
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    public static void sendToClient(net.minecraft.server.level.ServerPlayer player, DisplayTextPacket packet) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }
}

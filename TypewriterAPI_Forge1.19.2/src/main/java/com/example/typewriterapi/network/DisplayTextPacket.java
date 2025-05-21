package com.example.typewriterapi.network.packet;

import com.example.typewriterapi.client.TypewriterScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DisplayTextPacket {

    private String text;
    private float duration;
    private ResourceLocation sound;

    public DisplayTextPacket(String text, float duration, ResourceLocation sound) {
        this.text = text;
        this.duration = duration;
        this.sound = sound;
    }

    // Decoder constructor
    public DisplayTextPacket(FriendlyByteBuf buf) {
        this.text = buf.readUtf(32767);
        this.duration = buf.readFloat();
        this.sound = buf.readResourceLocation();
    }

    // Encoder
    public static void encode(DisplayTextPacket pkt, FriendlyByteBuf buf) {
        buf.writeUtf(pkt.text);
        buf.writeFloat(pkt.duration);
        buf.writeResourceLocation(pkt.sound);
    }

    // Handle packet on client thread
    public void handle() {
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            mc.setScreen(new TypewriterScreen(text, duration, sound));
        });
    }
}

package com.example.typewriterapi.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.ForgeRegistries;

public class TypewriterScreen extends Screen {

    private final String text;
    private final float durationSeconds;
    private final ResourceLocation soundLocation;

    private int ticksElapsed = 0;

    public TypewriterScreen(String text, float durationSeconds, ResourceLocation soundLocation) {
        super(Component.literal(""));
        this.text = text;
        this.durationSeconds = durationSeconds;
        this.soundLocation = soundLocation;
    }

    @Override
    protected void init() {
        super.init();
        playSound();
        System.out.println("[TypewriterScreen] init called");
    }

    private void playSound() {
        Minecraft mc = Minecraft.getInstance();
        SoundEvent soundEvent = SoundEvents.PLAYER_LEVELUP; // default

        if (soundLocation != null) {
            SoundEvent found = ForgeRegistries.SOUND_EVENTS.getValue(soundLocation);
            if (found != null) {
                soundEvent = found;
            }
        }

        mc.getSoundManager().play(SimpleSoundInstance.forUI(soundEvent, 1.0F));
    }

    @Override
    public void tick() {
        ticksElapsed++;
        if (ticksElapsed > durationSeconds * 20) {
            Minecraft.getInstance().setScreen(null);
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        // Fill background with translucent black
        fill(poseStack, 0, 0, this.width, this.height, 0xAA000000);

        // Calculate how many characters to show (one every 3 ticks)
        int maxChars = ticksElapsed / 3;
        if (maxChars > text.length()) maxChars = text.length();

        String displayText = text.substring(0, maxChars);

        int x = this.width / 2 - font.width(displayText) / 2;
        int y = this.height / 2;

        font.draw(poseStack, displayText, x, y, 0xFFFFFF);

        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

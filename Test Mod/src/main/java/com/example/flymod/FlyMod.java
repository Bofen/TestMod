package com.example.flymod;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(FlyMod.MODID)
public class FlyMod {
    public static final String MODID = "flymod";

    private static KeyMapping flyKey;
    private static boolean flyModeEnabled = false;

    public FlyMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            flyKey = new KeyMapping("key.flymod.fly", GLFW.GLFW_KEY_H, "key.categories.flymod");
            net.minecraftforge.client.ClientRegistry.registerKeyMapping(flyKey);
        });
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (flyKey.isDown()) {
            toggleFlyMode();
        }
    }

    private void toggleFlyMode() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            flyModeEnabled = !flyModeEnabled;
            player.getAbilities().mayfly = flyModeEnabled;
            player.getAbilities().flying = flyModeEnabled;
            player.onUpdateAbilities();
        }
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (flyModeEnabled) {
            Minecraft.getInstance().font.drawShadow(event.getPoseStack(),
                Component.literal("Fly mode enabled"),
                10, event.getWindow().getGuiScaledHeight() - 20, 0xFFFFFF);
        }
    }
}

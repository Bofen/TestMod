package com.example.flymod;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;

@Mod(FlyMod.MODID)
public class FlyMod {
    public static final String MODID = "flymod";

    public static KeyMapping TOGGLE_FLY_KEY;

    public FlyMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        // Key mapping is registered in RegisterKeyMappingsEvent
    }

    @SubscribeEvent
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        TOGGLE_FLY_KEY = new KeyMapping("key.flymod.toggle_fly", GLFW.GLFW_KEY_H, "key.categories.misc");
        event.register(TOGGLE_FLY_KEY);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (TOGGLE_FLY_KEY.isDown()) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            if (player != null) {
                player.getAbilities().mayfly = !player.getAbilities().mayfly;
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().flying = false;
                }
                Component message = player.getAbilities().mayfly ?
                    Component.literal("Fly Mode Enabled") :
                    Component.literal("Fly Mode Disabled");
                minecraft.gui.setOverlayMessage(message, false);
            }
        }
    }
}

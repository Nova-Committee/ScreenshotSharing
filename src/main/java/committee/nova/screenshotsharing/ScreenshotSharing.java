package committee.nova.screenshotsharing;

import com.mojang.brigadier.arguments.StringArgumentType;
import committee.nova.screenshotsharing.client.key.KeyMappings;
import committee.nova.screenshotsharing.util.Utilities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ScreenshotSharing implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyBindingHelper.registerKeyBinding(KeyMappings.screenshotAndShare);
        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
            boolean pressed = false;
            while (KeyMappings.screenshotAndShare.consumeClick()) pressed = true;
            if (!pressed) return;
            Utilities.sendScreenshot();
        });
        ClientCommandRegistrationCallback.EVENT.register((d, a) -> d.register(ClientCommandManager.literal("sharescreenshot")
                .then(
                        ClientCommandManager.argument("name", StringArgumentType.string())
                                .executes(ctx -> {
                                    Utilities.sendScreenshot(StringArgumentType.getString(ctx, "name"));
                                    return 1;
                                })
                                .requires(s -> true)
                )
                .executes(ctx -> {
                    Utilities.sendScreenshot();
                    return 1;
                })
                .requires(s -> true))
        );
    }
}

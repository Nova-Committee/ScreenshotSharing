package committee.nova.screenshotsharing.client.event;

import com.mojang.brigadier.arguments.StringArgumentType;
import committee.nova.screenshotsharing.client.key.KeyMappings;
import committee.nova.screenshotsharing.util.Utilities;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ForgeClientEventHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (!KeyMappings.screenshotAndShare.consumeClick()) return;
        Utilities.sendScreenshot();
    }

    @SubscribeEvent
    public static void onRegClientCmd(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("sharescreenshot")
                        .then(
                                Commands.argument("name", StringArgumentType.string())
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
                        .requires(s -> true)
        );
    }
}

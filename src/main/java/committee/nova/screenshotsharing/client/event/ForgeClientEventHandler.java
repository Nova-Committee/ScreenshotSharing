package committee.nova.screenshotsharing.client.event;

import committee.nova.screenshotsharing.client.key.KeyMappings;
import committee.nova.screenshotsharing.util.Utilities;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
                        .executes(ctx -> {

                            Utilities.sendScreenshot();
                            return 1;
                        })
                        .requires(s -> true)
        );
    }
}

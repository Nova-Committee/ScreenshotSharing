package committee.nova.screenshotsharing.client.event;

import committee.nova.screenshotsharing.client.key.KeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventHandler {
    @SubscribeEvent
    public static void onRegKey(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.screenshotAndShare);
    }
}

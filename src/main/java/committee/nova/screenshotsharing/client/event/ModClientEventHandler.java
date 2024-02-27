package committee.nova.screenshotsharing.client.event;

import committee.nova.screenshotsharing.client.key.KeyMappings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventHandler {
    @SubscribeEvent
    public static void onRegKey(RegisterKeyMappingsEvent event) {
        event.register(KeyMappings.screenshotAndShare);
    }
}

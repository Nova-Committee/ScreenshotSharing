package committee.nova.screenshotsharing.client.event;

import committee.nova.screenshotsharing.client.key.KeyMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModClientEventHandler {
    @SubscribeEvent
    public static void onRegKey(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KeyMappings.screenshotAndShare);
    }
}

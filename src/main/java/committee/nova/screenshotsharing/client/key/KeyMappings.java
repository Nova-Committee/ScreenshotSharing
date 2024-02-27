package committee.nova.screenshotsharing.client.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyMappings {
    public static final KeyBinding screenshotAndShare = new KeyBinding(
            "key.screenshotsharing.screenshot_and_share",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.NONE,
            InputMappings.Type.KEYSYM,
            -1,
            "key.screenshotsharing.category"
    );
}

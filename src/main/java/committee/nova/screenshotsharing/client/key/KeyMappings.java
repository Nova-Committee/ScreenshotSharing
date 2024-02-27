package committee.nova.screenshotsharing.client.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

public class KeyMappings {
    public static final KeyMapping screenshotAndShare = new KeyMapping(
            "key.screenshotsharing.screenshot_and_share",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.NONE,
            InputConstants.Type.KEYSYM,
            -1,
            "key.screenshotsharing.category"
    );
}

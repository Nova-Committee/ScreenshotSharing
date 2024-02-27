package committee.nova.screenshotsharing;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

@Mod("screenshotsharing")
public class ScreenshotSharing {
    public ScreenshotSharing() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(
                () -> "",
                (a, b) -> true
        ));
    }
}

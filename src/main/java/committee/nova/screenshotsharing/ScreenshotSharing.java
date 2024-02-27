package committee.nova.screenshotsharing;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod("screenshotsharing")
public class ScreenshotSharing {
    public ScreenshotSharing() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(
                "",
                (a, b) -> true
        ));
    }
}

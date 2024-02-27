package committee.nova.screenshotsharing.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ScreenshotEvent;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.minecraft.util.ScreenShotHelper.takeScreenshot;


public class Utilities {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    public static void sendScreenshot() {
        sendScreenshot(null);
    }

    public static void sendScreenshot(String name) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> _sendScreenshot(name));
        } else {
            _sendScreenshot(name);
        }
    }

    private static void _sendScreenshot(String name) {
        final Minecraft mc = Minecraft.getInstance();
        final NativeImage nativeimage = takeScreenshot(mc.getWindow().getHeight(), mc.getWindow().getWidth(), mc.getMainRenderTarget());
        final File file1 = new File(mc.gameDirectory, "screenshots");
        file1.mkdir();
        final File file2 = getFile(file1);

        final ScreenshotEvent event = ForgeHooksClient.onScreenshot(nativeimage, file2);
        if (event.isCanceled()) return;
        final File target = event.getScreenshotFile();
        Util.ioPool().execute(() -> {
            try {
                nativeimage.writeToFile(target);
                final ITextComponent component = new StringTextComponent(file2.getName()).withStyle(TextFormatting.UNDERLINE).withStyle((p_168608_) -> p_168608_.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath())));
                if (event.getResultMessage() != null) {
                    mc.execute(() -> mc.gui.getChat().addMessage(event.getResultMessage()));
                } else {
                    mc.execute(() -> mc.gui.getChat().addMessage(new TranslationTextComponent("screenshot.success", component)));
                }
                mc.execute(() -> {
                    if (mc.player == null) return;
                    mc.player.chat(String.format(
                            "[[CICode,url=file:\\\\\\%s,name=%s]]",
                            target.getAbsolutePath(),
                            name == null ? I18n.get("name.screenshotsharing.screenshot") : name
                    ));
                });
            } catch (Exception exception) {
                mc.execute(() -> mc.gui.getChat().addMessage(new TranslationTextComponent("screenshot.failure", exception.getMessage())));
            } finally {
                nativeimage.close();
            }
        });
    }

    private static File getFile(File pGameDirectory) {
        String s = DATE_FORMAT.format(new Date());
        int i = 1;

        while (true) {
            final File file1 = new File(pGameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
            if (!file1.exists()) return file1;
            ++i;
        }
    }
}

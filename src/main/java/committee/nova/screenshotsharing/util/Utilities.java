package committee.nova.screenshotsharing.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ScreenshotEvent;

import java.io.File;

import static net.minecraft.client.Screenshot.takeScreenshot;

public class Utilities {
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
        final NativeImage nativeimage = takeScreenshot(mc.getMainRenderTarget());
        final File file1 = new File(mc.gameDirectory, "screenshots");
        file1.mkdir();
        final File file2 = getFile(file1);

        final ScreenshotEvent event = ForgeHooksClient.onScreenshot(nativeimage, file2);
        if (event.isCanceled()) return;
        final File target = event.getScreenshotFile();
        Util.ioPool().execute(() -> {
            try {
                nativeimage.writeToFile(target);
                final Component component = Component.literal(file2.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle((p_168608_) -> p_168608_.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath())));
                if (event.getResultMessage() != null) {
                    mc.execute(() -> mc.gui.getChat().addMessage(event.getResultMessage()));
                } else {
                    mc.execute(() -> mc.gui.getChat().addMessage(Component.translatable("screenshot.success", component)));
                }
                mc.execute(() -> {
                    if (mc.player == null) return;
                    final String msg = String.format(
                            "[[CICode,url=file:\\\\\\%s,name=%s]]",
                            target.getAbsolutePath(),
                            name == null ? I18n.get("name.screenshotsharing.screenshot") : name
                    );
                    mc.player.chatSigned(msg, Component.literal(msg));
                });
            } catch (Exception exception) {
                mc.execute(() -> mc.gui.getChat().addMessage(Component.translatable("screenshot.failure", exception.getMessage())));
            } finally {
                nativeimage.close();
            }
        });
    }

    private static File getFile(File pGameDirectory) {
        String s = Util.getFilenameFormattedDateTime();
        int i = 1;

        while (true) {
            final File file1 = new File(pGameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
            if (!file1.exists()) return file1;
            ++i;
        }
    }
}

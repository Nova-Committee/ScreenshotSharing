package committee.nova.screenshotsharing.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;

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
        final File file1 = new File(mc.gameDirectory, "screenshots");
        file1.mkdir();
        final File target = getFile(file1);
        Util.ioPool().execute(() -> {
            try (final NativeImage nativeimage = takeScreenshot(mc.getMainRenderTarget())) {
                nativeimage.writeToFile(target);
                final Component component = Component.literal(target.getName()).withStyle(ChatFormatting.UNDERLINE).withStyle((p_168608_) -> p_168608_.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, target.getAbsolutePath())));
                mc.execute(() -> mc.gui.getChat().addMessage(Component.translatable("screenshot.success", component)));
                mc.execute(() -> {
                    if (mc.getConnection() == null) return;
                    mc.getConnection().sendChat(
                            String.format(
                                    "[[CICode,url=file:\\\\\\%s,name=%s]]",
                                    target.getAbsolutePath(),
                                    name == null ? I18n.get("name.screenshotsharing.screenshot") : name
                            ));
                });
            } catch (Exception exception) {
                mc.execute(() -> mc.gui.getChat().addMessage(Component.translatable("screenshot.failure", exception.getMessage())));
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

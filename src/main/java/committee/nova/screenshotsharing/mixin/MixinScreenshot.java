package committee.nova.screenshotsharing.mixin;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.client.event.ScreenshotEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenShotHelper.class)
public abstract class MixinScreenshot {
    @Inject(
            method = "lambda$_grab$2",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private static void inject$_grab$1(NativeImage nativeimage, File target, File file2, ScreenshotEvent event, Consumer<ITextComponent> consumer, CallbackInfo ci) {
        screenshotsharing$appendShareButton(consumer, target);
    }

    @Inject(
            method = "lambda$_grab$2",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
                    ordinal = 1
            )
    )
    private static void inject$_grab$2(NativeImage nativeimage, File target, File file2, ScreenshotEvent event, Consumer<ITextComponent> consumer, CallbackInfo ci) {
        screenshotsharing$appendShareButton(consumer, target);
    }

    @Unique
    private static void screenshotsharing$appendShareButton(Consumer<ITextComponent> consumer, File target) {
        consumer.accept(new TranslationTextComponent("msg.screenshotsharing.share2chat")
                .withStyle(TextFormatting.GREEN)
                .withStyle(s -> s.withClickEvent(new ClickEvent(
                        ClickEvent.Action.SUGGEST_COMMAND,
                        String.format(
                                "/chatimage send \"%s\" file:\\\\\\%s",
                                I18n.get("name.screenshotsharing.screenshot"),
                                target.getAbsolutePath()
                        )
                )))
        );
    }
}

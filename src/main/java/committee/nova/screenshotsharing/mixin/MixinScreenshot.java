package committee.nova.screenshotsharing.mixin;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Screenshot;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.function.Consumer;

@Mixin(Screenshot.class)
public abstract class MixinScreenshot {
    @Inject(
            method = "method_1661",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
                    ordinal = 0
            )
    )
    private static void inject$_grab$1(NativeImage nativeimage, File target, Consumer<Component> consumer, CallbackInfo ci) {
        screenshotsharing$appendShareButton(consumer, target);
    }

    @Inject(
            method = "method_1661",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
                    ordinal = 1
            )
    )
    private static void inject$_grab$2(NativeImage nativeimage, File target, Consumer<Component> consumer, CallbackInfo ci) {
        screenshotsharing$appendShareButton(consumer, target);
    }

    @Unique
    private static void screenshotsharing$appendShareButton(Consumer<Component> consumer, File target) {
        consumer.accept(Component
                .translatable("msg.screenshotsharing.share2chat")
                .withStyle(ChatFormatting.GREEN)
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

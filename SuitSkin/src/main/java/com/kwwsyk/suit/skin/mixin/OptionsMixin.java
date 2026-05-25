package com.kwwsyk.suit.skin.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Objects;
import java.util.function.Consumer;

@Mixin(Options.class)
public class OptionsMixin {

    // Change only the "values" argument of the gamma OptionInstance constructor call.
    @ModifyArg(
            method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/OptionInstance;<init>(Ljava/lang/String;" +
                            "Lnet/minecraft/client/OptionInstance$TooltipSupplier;" +
                            "Lnet/minecraft/client/OptionInstance$CaptionBasedToString;" +
                            "Lnet/minecraft/client/OptionInstance$ValueSet;" +
                            "Ljava/lang/Object;" +
                            "Ljava/util/function/Consumer;)V"
            ),
            index = 3,
            slice = @Slice(
                    from = @At(value = "CONSTANT", args = "stringValue=options.gamma"),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;gamma:Lnet/minecraft/client/OptionInstance;", opcode = Opcodes.PUTFIELD)
            ),
            require = 1
    )
    private <T> OptionInstance.ValueSet<Double> suit$replaceGammaValueSet(String caption,
                                                                      OptionInstance.TooltipSupplier<T> tooltip,
                                                                      OptionInstance.CaptionBasedToString<T> valueStringifier,
                                                                      OptionInstance.ValueSet<Double> original,
                                                                      Object initialValue,
                                                                      Consumer<T> onValueUpdate
    ) {
        // Example: expand gamma range from [0, 1] to [0, 2].
        // The slider remains 0..1, but the stored value becomes 0..2.
        if(!(original instanceof OptionInstance.UnitDouble) || !Objects.equals(caption, "options.gamma")) return original;
        return ((OptionInstance.UnitDouble) original).xmap(
                slider -> slider * 15.0D,
                value -> value / 15.0D
        );
    }
}

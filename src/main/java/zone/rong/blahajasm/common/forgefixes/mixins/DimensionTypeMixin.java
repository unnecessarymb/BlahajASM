package zone.rong.blahajasm.common.forgefixes.mixins;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    private static DimensionType[] blahajRealValues = new DimensionType[] { DimensionType.OVERWORLD, DimensionType.NETHER, DimensionType.THE_END };

    @Inject(method = "register", at = @At("TAIL"), remap = false)
    private static void storeRegDimension(String name, String suffix, int id, Class<? extends WorldProvider> provider, boolean keepLoaded, CallbackInfoReturnable<DimensionType> cir) {
        DimensionType dim = cir.getReturnValue();
        DimensionType[] newArray = new DimensionType[blahajRealValues.length + 1];
        int i;
        for (i = 0; i < blahajRealValues.length; i++) {
            newArray[i] = blahajRealValues[i];
        }
        newArray[i] = dim;
        blahajRealValues = newArray;
    }

    @Inject(method = "values", at = @At("HEAD"), cancellable = true, remap = false)
    private static void getValues(CallbackInfoReturnable<DimensionType[]> cir) {
        cir.setReturnValue(blahajRealValues);
    }

}

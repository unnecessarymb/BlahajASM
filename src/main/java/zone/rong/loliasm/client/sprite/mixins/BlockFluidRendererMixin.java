package zone.rong.loliasm.client.sprite.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockFluidRenderer;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import zone.rong.loliasm.client.sprite.IAnimatedSpritePrimer;
import zone.rong.loliasm.client.sprite.ICompiledChunkExpander;

@Mixin(BlockFluidRenderer.class)
public class BlockFluidRendererMixin {

    @ModifyVariable(method = "renderFluid", at = @At(value = "CONSTANT", args = "floatValue=0.001", ordinal = 1), ordinal = 0)
    private TextureAtlasSprite afterTextureDetermined(TextureAtlasSprite texture) {
        if (!Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            CompiledChunk chunk = IAnimatedSpritePrimer.CURRENT_COMPILED_CHUNK.get();
            if (chunk != CompiledChunk.DUMMY) {
                ((ICompiledChunkExpander) chunk).resolve(texture);
            }
        }
        return texture;
    }

}

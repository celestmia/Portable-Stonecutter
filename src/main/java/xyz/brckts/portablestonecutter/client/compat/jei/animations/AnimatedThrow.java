package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;

public class AnimatedThrow implements IDrawable {

    private final ITickTimer tickTimer;
    private static final int frameCount = 4;

    public AnimatedThrow(ITickTimer tickTimer) {
        this.tickTimer = tickTimer;
    }

    public int getFrameCount() {
        return frameCount;
    }

    private int getCurrentFrame() {
        return this.tickTimer.getValue() % frameCount;
    }

    @Override
    public int getWidth() {
        return 64;
    }

    @Override
    public int getHeight() {
        return 64;
    }

    @Override
    public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
        RenderSystem.enableDepthTest();
        matrixStack.translate(xOffset, yOffset, 100);
    }

    public static void renderItemIntoGUI(MatrixStack matrixStack, ItemStack stack) {
        ItemRenderer renderer = Minecraft.getInstance()
                .getItemRenderer();
        IBakedModel bakedModel = renderer.getItemModelWithOverrides(stack, null, null);
        matrixStack.push();
        renderer.textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        renderer.textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE)
                .setBlurMipmapDirect(false, false);
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.translate((float) 0, (float) 0, 100.0F + renderer.zLevel);
        matrixStack.translate(8.0F, 8.0F, 0.0F);
        matrixStack.scale(16.0F, 16.0F, 16.0F);
        IRenderTypeBuffer.Impl irendertypebuffer$impl = Minecraft.getInstance()
                .getBufferBuilders()
                .getEntityVertexConsumers();
        boolean flag = !bakedModel.isSideLit();
        if (flag) {
            RenderHelper.disableGuiDepthLighting();
        }

        renderer.renderItem(stack, ItemCameraTransforms.TransformType.GUI, false, matrixStack,
                irendertypebuffer$impl, 15728880, OverlayTexture.DEFAULT_UV, bakedModel);
        irendertypebuffer$impl.draw();
        RenderSystem.enableDepthTest();
        if (flag) {
            RenderHelper.enableGuiDepthLighting();
        }

        RenderSystem.disableAlphaTest();
        RenderSystem.disableRescaleNormal();
        RenderSystem.enableCull();
        matrixStack.pop();
    }
}

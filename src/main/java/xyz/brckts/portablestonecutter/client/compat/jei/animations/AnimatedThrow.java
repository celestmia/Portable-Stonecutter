package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;


public class AnimatedThrow implements IDrawable {

    private final IDrawableStatic drawable;
    private final ITickTimer tickTimer;
    private final int frameCount;

    public AnimatedThrow(IDrawableStatic drawable, ITickTimer tickTimer, int frameCount) {
        this.drawable = drawable;
        this.tickTimer = tickTimer; // between 0 and frameCount - 1
        this.frameCount = frameCount;
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
        int currentFrame = tickTimer.getValue();

        int maskLeft = (currentFrame % 4) * 64;
        int maskRight = (3 - currentFrame % 4) * 64;
        int maskTop = (currentFrame / 4) * 64;
        int maskBottom = (3 - currentFrame / 4) * 64;

        drawable.draw(matrixStack, xOffset - maskLeft, yOffset - maskTop, maskTop, maskBottom, maskLeft, maskRight);
    }
}

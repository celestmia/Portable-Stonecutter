package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;


public class AnimatedSprite implements IDrawable {

    private final IDrawableStatic drawable;
    private final ITickTimer tickTimer;
    private final int frameCount;
    private final int frameWidth;
    private final int frameHeight;

    public AnimatedSprite(IDrawableStatic drawable, ITickTimer tickTimer, int frameCount, int frameWidth, int frameHeight) {
        this.drawable = drawable;
        this.tickTimer = tickTimer; // between 0 and frameCount - 1
        this.frameCount = frameCount;
        this.frameHeight = frameHeight;
        this.frameWidth = frameWidth;
    }

    public AnimatedSprite(IDrawableStatic drawable, ITickTimer tickTimer) {
        this.drawable = drawable;
        this.tickTimer = tickTimer;
        this.frameCount = 16;
        this.frameWidth = 64;
        this.frameHeight = 64;
    }

    public AnimatedSprite(IDrawableStatic drawable, ITickTimer tickTimer, int frameCount) {
        this.drawable = drawable;
        this.tickTimer = tickTimer;
        this.frameCount = frameCount;
        this.frameWidth = 64;
        this.frameHeight = 64;
    }

    private void drawFrame(MatrixStack matrixStack, int xOffset, int yOffset, int frame) {
        int drawableWidth = drawable.getWidth();
        int drawableHeight = drawable.getHeight();
        int columns = drawableWidth / frameWidth;
        int lines = drawableHeight / frameHeight;
        int maskLeft = (frame % columns) * frameWidth;
        int maskRight = (columns - frame % columns - 1) * frameWidth;
        int maskTop = (frame / lines) * frameHeight;
        int maskBottom = (lines - frame / lines - 1) * frameHeight;

        drawable.draw(matrixStack, xOffset - maskLeft, yOffset - maskTop, maskTop, maskBottom, maskLeft, maskRight);
    }

    public int getFrameCount() {
        return frameCount;
    }

    @Override
    public int getWidth() {
        return frameWidth;
    }

    @Override
    public int getHeight() {
        return frameHeight;
    }

    @Override
    public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
        int currentFrame = tickTimer.getValue() % this.frameCount;

        drawFrame(matrixStack, xOffset, yOffset, currentFrame);
    }
}

package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ITickTimer;
import mezz.jei.api.gui.drawable.IDrawable;


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
    }
}

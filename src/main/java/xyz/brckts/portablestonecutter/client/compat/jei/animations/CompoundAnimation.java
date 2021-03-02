package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.ITickTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundAnimation {

    private final List<Frame> frames = new ArrayList<>();
    private final ITickTimer tickTimer;
    private final AnimatedSprite animatedSprite;
    private final int frameCount;

    public CompoundAnimation(ITickTimer tickTimer, AnimatedSprite animatedSprite, Frame... frames) {
        this.tickTimer = tickTimer;
        this.animatedSprite = animatedSprite;
        this.frames.addAll(Arrays.asList(frames));
        this.frameCount = this.animatedSprite.getFrameCount();
    }

    public void draw(MatrixStack matrixStack, int xOffset, int yOffset) {
        int currentFrame = tickTimer.getValue() % frameCount;

        matrixStack.translate(xOffset, yOffset, 0);
        animatedSprite.draw(matrixStack);
        if (frames.size() > currentFrame)
            frames.get(currentFrame).draw(matrixStack);
        matrixStack.translate(-xOffset, -yOffset, 0);
    }
}
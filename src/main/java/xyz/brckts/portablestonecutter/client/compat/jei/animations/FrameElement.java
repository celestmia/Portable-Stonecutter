package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.gui.drawable.IDrawable;

public class FrameElement {

    private final IDrawable drawable;
    private final int xOffset;
    private final int yOffset;

    public FrameElement(IDrawable drawable, int xOffset, int yOffset) {
        this.drawable = drawable;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void draw(MatrixStack matrixStack) {
        this.drawable.draw(matrixStack, this.xOffset, this.yOffset);
    }
}

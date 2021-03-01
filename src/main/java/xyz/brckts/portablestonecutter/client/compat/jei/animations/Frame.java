package xyz.brckts.portablestonecutter.client.compat.jei.animations;

import com.mojang.blaze3d.matrix.MatrixStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frame {
    private final List<FrameElement> elements = new ArrayList<>();

    public Frame(FrameElement... els) {
        elements.addAll(Arrays.asList(els));
    }

    public void draw(MatrixStack matrixStack) {
        for (FrameElement el : elements) {
            el.draw(matrixStack);
        }
    }
}

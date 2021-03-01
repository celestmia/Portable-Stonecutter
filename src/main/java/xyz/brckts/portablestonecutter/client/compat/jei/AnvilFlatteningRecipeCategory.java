package xyz.brckts.portablestonecutter.client.compat.jei;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.block.Blocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import xyz.brckts.portablestonecutter.PortableStonecutter;
import xyz.brckts.portablestonecutter.api.IAnvilFlatteningRecipe;
import xyz.brckts.portablestonecutter.client.compat.jei.animations.AnimatedSprite;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnvilFlatteningRecipeCategory implements IRecipeCategory<IAnvilFlatteningRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(PortableStonecutter.MOD_ID, "anvil_flattening");
    private static final ResourceLocation texture = new ResourceLocation(PortableStonecutter.MOD_ID, "textures/gui/jei_anvil_flattening.png");
    private static final ResourceLocation animation = new ResourceLocation(PortableStonecutter.MOD_ID, "textures/gui/animations/throw.png");

    private final IDrawableStatic background;
    private final IDrawable icon;
    private final String title;
    private final IDrawableStatic overlay;
    private final AnimatedSprite animatedSprite;
    private final int RECIPE_WIDTH = 128;
    private final int RECIPE_HEIGHT = 64;

    public AnvilFlatteningRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(RECIPE_WIDTH, RECIPE_HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.ANVIL));
        this.title = I18n.format("jei." + UID.toString());
        this.overlay = guiHelper.createDrawable(texture, 0, 0, 128, 64);
        this.animatedSprite = new AnimatedSprite(guiHelper.createDrawable(animation, 0, 0, 256, 256), guiHelper.createTickTimer(100, 15, false));
    }

    @Nonnull
    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends IAnvilFlatteningRecipe> getRecipeClass() {
        return IAnvilFlatteningRecipe.class;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return title;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(IAnvilFlatteningRecipe recipe, IIngredients ingredients) {
        List<List<ItemStack>> list = new ArrayList<>();
        for (Ingredient ingr : recipe.getIngredients()) {
            list.add(Arrays.asList(ingr.getMatchingStacks()));
        }
        ingredients.setInputLists(VanillaTypes.ITEM, list);
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IAnvilFlatteningRecipe recipe, IIngredients ingredients) {
        int lines = RECIPE_HEIGHT / 16;
        int width = RECIPE_WIDTH / 2;
        int index = 0;
        int ingrCnt = ingredients.getInputs(VanillaTypes.ITEM).size();
        int ingrPerLine = (ingrCnt > lines ? ingrCnt / lines : ingrCnt);
        int spaceBetweenEach = width / ingrPerLine;

        for (List<ItemStack> o : ingredients.getInputs(VanillaTypes.ITEM)) {
            recipeLayout.getItemStacks().init(index, true, width + (index % ingrPerLine) * spaceBetweenEach, (index / ingrPerLine) * 16);
            recipeLayout.getItemStacks().set(index, o);
            index++;
        }
    }

    @Override
    public void draw(IAnvilFlatteningRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        overlay.draw(matrixStack);
        animatedSprite.draw(matrixStack, 0, 0);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
    }
}

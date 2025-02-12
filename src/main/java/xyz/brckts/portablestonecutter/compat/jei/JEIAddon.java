package xyz.brckts.portablestonecutter.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import xyz.brckts.portablestonecutter.PortableStonecutter;
import xyz.brckts.portablestonecutter.items.crafting.AnvilFlatteningRecipe;
import xyz.brckts.portablestonecutter.util.RegistryHandler;

import java.util.List;

@JeiPlugin
public class JEIAddon implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(PortableStonecutter.MOD_ID, "jei");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Items.ANVIL), AnvilFlatteningRecipeCategory.RECIPE_TYPE);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        List<AnvilFlatteningRecipe> anvilFlatteningRecipes = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(RegistryHandler.ANVIL_FLATTENING_RECIPE_TYPE.get()).stream().map(rh -> rh.value()).toList();

        registration.addRecipes(AnvilFlatteningRecipeCategory.RECIPE_TYPE, anvilFlatteningRecipes);

        for (var recipe: anvilFlatteningRecipes) {
            registration.addIngredientInfo(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()), VanillaTypes.ITEM_STACK, Component.translatable("info." + PortableStonecutter.MOD_ID + ":anvil_flattening"));
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new AnvilFlatteningRecipeCategory((guiHelper)));
    }
}

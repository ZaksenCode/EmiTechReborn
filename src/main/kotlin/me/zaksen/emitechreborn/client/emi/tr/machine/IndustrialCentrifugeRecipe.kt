package me.zaksen.emitechreborn.client.emi.tr.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeEntry
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class IndustrialCentrifugeRecipe(
    category: EmiRecipeCategory,
    recipe: RecipeEntry<out RebornRecipe>
): AbstractMachineRecipe(
    category,
    recipe,
    120,
    70
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 31, 17)
        widgets.addSlot(input.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 31, 37)

        widgets.addSlot(output[0], 69, 27).recipeContext(this)
        widgets.addSlot(output.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 82, 8).recipeContext(this)
        widgets.addSlot(output.getOrElse(2) { _ -> EmiIngredient.of(Ingredient.empty())}, 95, 27).recipeContext(this)
        widgets.addSlot(output.getOrElse(3) { _ -> EmiIngredient.of(Ingredient.empty())}, 82, 46).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.value.time() / 20.0)),
            5,
            1,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        )
        widgets.add(ProgressBarWidget(51, 31, recipe.value.time() * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(EnergyDisplayWidget(5, 10, recipe.value.power(), recipe.value.power() * recipe.value.time()))
    }
}
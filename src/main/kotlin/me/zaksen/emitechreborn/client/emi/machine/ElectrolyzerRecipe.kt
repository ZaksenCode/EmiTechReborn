package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class ElectrolyzerRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 22, 42)
        widgets.addSlot(input.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 42, 42)
        widgets.addSlot(output[0], 33, 16).recipeContext(this)
        widgets.addSlot(output.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 51, 16).recipeContext(this)
        widgets.addSlot(output.getOrElse(2) { _ -> EmiIngredient.of(Ingredient.empty())}, 69, 16).recipeContext(this)
        widgets.addSlot(output.getOrElse(3) { _ -> EmiIngredient.of(Ingredient.empty())}, 87, 16).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            xSize - 4,
            ySize - 12,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)
        widgets.add(ProgressBarWidget(64, 43, recipe.time * 50.0, GuiBuilder.ProgressDirection.UP))
        widgets.add((EnergyDisplayWidget(5, 10, recipe.power, recipe.power * recipe.time)))
    }
}
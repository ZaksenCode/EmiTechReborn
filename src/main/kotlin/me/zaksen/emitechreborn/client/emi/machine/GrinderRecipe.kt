package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.CustomTankWidget
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornFluidRecipe
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class GrinderRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe
): AbstractMachineRecipe(
    category,
    recipe,
    120,
    72
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 50, 27)

        if(recipe is RebornFluidRecipe) {
            val fluidStack = EmiStack.of(recipe.fluidInstance.fluid)
            fluidStack.amount = recipe.fluidInstance.amount.rawValue
            widgets.add(CustomTankWidget(fluidStack, 22, 7, 810000))
        }

        widgets.addSlot(output[0], 96, 0).recipeContext(this)
        widgets.addSlot(output.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 96, 18).recipeContext(this)
        widgets.addSlot(output.getOrElse(2) { _ -> EmiIngredient.of(Ingredient.empty())}, 96, 36).recipeContext(this)
        widgets.addSlot(output.getOrElse(3) { _ -> EmiIngredient.of(Ingredient.empty())}, 96, 54).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            50,
            2,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        )
        widgets.add(ProgressBarWidget(74, 31, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(EnergyDisplayWidget(2, 10, recipe.power, recipe.power * recipe.time))
    }
}
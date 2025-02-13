package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.CustomTankWidget
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.RecipeEntry
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornFluidRecipe
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class SawmillMachineRecipe(
    category: EmiRecipeCategory,
    recipe: RecipeEntry<out RebornRecipe>
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 50, 27)

        if(recipe.value is RebornFluidRecipe) {
            val fRecipe = recipe.value as RebornFluidRecipe
            val fluidStack = EmiStack.of(fRecipe.fluid().fluid())
            fluidStack.amount = fRecipe.fluid().amount.rawValue()
            widgets.add(CustomTankWidget(fluidStack, 22, 7, 810000))
        }

        widgets.addSlot(output[0], 96, 9).recipeContext(this)
        widgets.addSlot(output.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}, 96, 27).recipeContext(this)
        widgets.addSlot(output.getOrElse(2) { _ -> EmiIngredient.of(Ingredient.empty())}, 96, 45).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.value.time() / 20.0)),
            50,
            2,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        )
        widgets.add(ProgressBarWidget(74, 31, recipe.value.time() * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(EnergyDisplayWidget(2, 10, recipe.value.power(), recipe.value.power() * recipe.value.time()))
    }
}
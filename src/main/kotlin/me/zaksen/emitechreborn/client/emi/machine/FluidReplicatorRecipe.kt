package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.EmiPlugin
import me.zaksen.emitechreborn.client.emi.widget.CustomTankWidget
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import techreborn.api.recipe.recipes.FluidReplicatorRecipe
import java.text.DecimalFormat

class FluidReplicatorRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun getOutputs(): MutableList<EmiStack> {
        val output = mutableListOf<EmiStack>()

        if(recipe is FluidReplicatorRecipe) {
            val fluidStack = EmiStack.of(recipe.fluidInstance.fluid)
            fluidStack.amount = recipe.fluidInstance.amount.rawValue
            output.add(fluidStack)
        }

        return output
    }

    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 32, 26)

        if(recipe is FluidReplicatorRecipe) {
            val fluidStack = EmiStack.of(recipe.fluidInstance.fluid)
            fluidStack.amount = recipe.fluidInstance.amount.rawValue
            widgets.add(CustomTankWidget(fluidStack, 72, 7, 810000).recipeContext(this))
        }

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            5,
            1,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        )
        widgets.add(ProgressBarWidget(53, 30, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add((EnergyDisplayWidget(5, 10, recipe.power, recipe.power * recipe.time)))
    }
}
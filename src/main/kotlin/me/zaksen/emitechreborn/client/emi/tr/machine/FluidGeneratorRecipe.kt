package me.zaksen.emitechreborn.client.emi.tr.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.CustomTankWidget
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.RecipeEntry
import reborncore.client.gui.GuiBuilder
import techreborn.recipe.recipes.FluidGeneratorRecipe

class FluidGeneratorRecipe(
    private val category: EmiRecipeCategory,
    recipe: RecipeEntry<FluidGeneratorRecipe>
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun getCategory(): EmiRecipeCategory {
        return category
    }

    override fun getDisplayWidth(): Int {
        return 119
    }

    override fun getDisplayHeight(): Int {
        return 69
    }

    override fun addWidgets(widgets: WidgetHolder) {
        if (recipe.value is FluidGeneratorRecipe) {
            val fRecipe = recipe.value as FluidGeneratorRecipe
            val fluidStack = EmiStack.of(fRecipe.fluid())
            widgets.add(CustomTankWidget(fluidStack, 5, 7, 810000))
            widgets.add(ProgressBarWidget((displayWidth / 2) - 8, (displayHeight / 2) - 5, 500.0, GuiBuilder.ProgressDirection.RIGHT))
            widgets.add((EnergyDisplayWidget(displayWidth - 14 - 5, 10, fRecipe.power, fRecipe.power)))
        }
    }
}
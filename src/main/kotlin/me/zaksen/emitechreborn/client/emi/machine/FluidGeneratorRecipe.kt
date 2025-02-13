package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.CustomTankWidget
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.util.Identifier
import reborncore.client.gui.GuiBuilder
import techreborn.api.generator.FluidGeneratorRecipe

class FluidGeneratorRecipe(
    private val category: EmiRecipeCategory,
    private val display: FluidGeneratorRecipe,
    private val _id: Identifier
): EmiRecipe {
    override fun getCategory(): EmiRecipeCategory {
        return category
    }

    override fun getId(): Identifier {
        return _id
    }

    override fun getInputs(): MutableList<EmiIngredient> {
        return mutableListOf(EmiStack.of(display.fluid))
    }

    override fun getOutputs(): MutableList<EmiStack> {
        return mutableListOf()
    }

    override fun getDisplayWidth(): Int {
        return 119
    }

    override fun getDisplayHeight(): Int {
        return 69
    }

    override fun addWidgets(widgets: WidgetHolder) {
        val fluidStack = EmiStack.of(display.fluid)
        widgets.add(CustomTankWidget(fluidStack, 5, 7, 810000))
        widgets.add(ProgressBarWidget((displayWidth / 2) - 8, (displayHeight / 2) - 5, 500.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add((EnergyDisplayWidget(displayWidth - 14 - 5, 10, display.energyPerMb, display.energyPerMb)))
    }
}
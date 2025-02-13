package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class AssemblingMachineRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe
): AbstractMachineRecipe(
    category,
    recipe,
    120,
    70
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 32, 16)
        widgets.addSlot(input[1], 32, 38)

        widgets.addSlot(output[0], 70, 27).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            xSize - 4,
            4,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)
        widgets.add(ProgressBarWidget(52, 31, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add((EnergyDisplayWidget(5, 10, recipe.power, recipe.power * recipe.time)))
    }
}
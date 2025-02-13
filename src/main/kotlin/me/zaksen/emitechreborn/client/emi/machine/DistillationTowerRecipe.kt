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
import techreborn.client.compat.rei.ReiPlugin
import java.text.DecimalFormat

class DistillationTowerRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 22, 13)
        widgets.addSlot(input[1], 22, 35)

        widgets.addSlot(output[0], 60, 25).recipeContext(this)
        widgets.addSlot(output[1], 78, 25).recipeContext(this)
        widgets.addSlot(output[2], 96, 25).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            xSize - 4,
            4,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)

        widgets.add(ProgressBarWidget(42, 29, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add((EnergyDisplayWidget(5, 10, recipe.power, recipe.power * recipe.time)))
    }

}
package me.zaksen.emitechreborn.client.emi.tr.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.RecipeEntry
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import java.text.DecimalFormat

class TwoInputsCenterOutputRecipe(
    category: EmiRecipeCategory,
    recipe: RecipeEntry<out RebornRecipe>
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 19, 25)
        widgets.addSlot(input[1], 91, 25)

        widgets.addSlot(output[0], 55, 25).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.value.time() / 20.0)),
            xSize - 4,
            4,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)

        widgets.add(ProgressBarWidget(38, 29, recipe.value.time() * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(ProgressBarWidget(74, 29, recipe.value.time() * 50.0, GuiBuilder.ProgressDirection.LEFT))
        widgets.add(EnergyDisplayWidget(2, 10, recipe.value.power(), recipe.value.power() * recipe.value.time()))
    }
}
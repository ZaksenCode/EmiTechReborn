package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeEntry
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import techreborn.recipe.recipes.RollingMachineRecipe
import java.text.DecimalFormat

class RollingMachineRecipe(
    category: EmiRecipeCategory,
    recipe: RecipeEntry<RollingMachineRecipe>
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    64
) {
    override fun getInputs(): MutableList<EmiIngredient> {
        val rRecipe = recipe.value as RollingMachineRecipe
        return rRecipe.ingredients.map { EmiIngredient.of(it) }.toMutableList()
    }

    override fun addWidgets(widgets: WidgetHolder) {
        for (i in 0..8) {
            if (i < inputs.size) {
                widgets.addSlot(inputs[i], 18 + i % 3 * 18, 7 + i / 3 * 18)
            } else {
                widgets.addSlot(EmiStack.of(ItemStack.EMPTY), 18 + i % 3 * 18, 7 + i / 3 * 18)
            }
        }

        widgets.addSlot(output[0], 96, 25).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.value.time() / 20.0)),
            displayWidth - 4,
            4,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)
        widgets.add(ProgressBarWidget(75, 29, recipe.value.time() * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(EnergyDisplayWidget(2, 10, recipe.value.power(), recipe.value.power() * recipe.value.time()))
    }
}
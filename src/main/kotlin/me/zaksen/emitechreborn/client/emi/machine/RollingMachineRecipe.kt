package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.widget.SlotWidget
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import techreborn.api.recipe.recipes.RollingMachineRecipe
import java.text.DecimalFormat

class RollingMachineRecipe(
    private val _category: EmiRecipeCategory,
    private val recipe: RollingMachineRecipe
): EmiCraftingRecipe(
    recipe.ingredients.map { EmiIngredient.of(it) },
    EmiStack.of(recipe.getOutput(null)),
    recipe.id,
    false
) {
    override fun getCategory(): EmiRecipeCategory {
        return _category
    }

    override fun getDisplayHeight(): Int {
        return 64
    }

    override fun getDisplayWidth(): Int {
        return 119
    }

    override fun addWidgets(widgets: WidgetHolder) {
        for (i in 0..8) {
            if (i < input.size) {
                widgets.addSlot(input[i], 18 + i % 3 * 18, 7 + i / 3 * 18)
            } else {
                widgets.addSlot(EmiStack.of(ItemStack.EMPTY), 18 + i % 3 * 18, 7 + i / 3 * 18)
            }
        }

        widgets.addSlot(output, 96, 25).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            displayWidth - 4,
            4,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        ).horizontalAlign(TextWidget.Alignment.END)
        widgets.add(ProgressBarWidget(75, 29, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add(EnergyDisplayWidget(2, 10, recipe.power, recipe.power * recipe.time))
    }
}
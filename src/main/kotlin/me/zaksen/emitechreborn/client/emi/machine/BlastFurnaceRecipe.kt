package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.widget.TextWidget
import dev.emi.emi.api.widget.WidgetHolder
import me.zaksen.emitechreborn.client.emi.widget.EnergyDisplayWidget
import me.zaksen.emitechreborn.client.emi.widget.ProgressBarWidget
import net.minecraft.recipe.Ingredient
import net.minecraft.text.Text
import net.minecraft.util.math.ColorHelper
import reborncore.client.gui.GuiBuilder
import reborncore.common.crafting.RebornRecipe
import techreborn.api.recipe.recipes.BlastFurnaceRecipe
import java.text.DecimalFormat

class BlastFurnaceRecipe(
    category: EmiRecipeCategory,
    recipe: RebornRecipe,
): AbstractMachineRecipe(
    category,
    recipe,
    119,
    69
) {
    override fun addWidgets(widgets: WidgetHolder) {
        widgets.addSlot(input[0], 27, 15)
        val secondInput = input.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}
        widgets.addSlot(secondInput, 27, 37)

        widgets.addSlot(output[0], 65, 27).recipeContext(this)
        val secondOutput = output.getOrElse(1) { _ -> EmiIngredient.of(Ingredient.empty())}
        widgets.addSlot(secondOutput, 83, 27).recipeContext(this)

        widgets.addText(
            Text.translatable("techreborn.jei.recipe.processing.time.3", DecimalFormat("###.##").format(recipe.time / 20.0)),
            5,
            1,
            ColorHelper.Argb.getArgb(255, 64, 64, 64),
            false
        )

        if(recipe is BlastFurnaceRecipe) {
            widgets.addText(
                Text.literal((recipe.heat.toString())).append(" ").append(Text.translatable("techreborn.jei.recipe.heat")),
                xSize - 4,
                1,
                ColorHelper.Argb.getArgb(255, 64, 64, 64),
                false
            ).horizontalAlign(TextWidget.Alignment.END)
        }

        widgets.add(ProgressBarWidget(47, 30, recipe.time * 50.0, GuiBuilder.ProgressDirection.RIGHT))
        widgets.add((EnergyDisplayWidget(5, 10, recipe.power, recipe.power * recipe.time)))
    }
}
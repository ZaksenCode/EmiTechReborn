package me.zaksen.emitechreborn.client.emi.machine

import dev.emi.emi.api.recipe.EmiRecipe
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.util.Identifier
import reborncore.common.crafting.RebornRecipe

abstract class AbstractMachineRecipe(
    private val category: EmiRecipeCategory,
    protected val recipe: RebornRecipe,
    protected val xSize: Int,
    protected val ySize: Int
): EmiRecipe {

    protected val input by lazy {
        val result = mutableListOf<EmiIngredient>()

        recipe.rebornIngredients.forEach {
            result.add(EmiIngredient.of(it.preview))
        }

        result
    }

    protected val output by lazy {
        val result = mutableListOf<EmiStack>()

        recipe.getOutputs(null).forEach {
            result.add(EmiStack.of(it))
        }

        result
    }

    override fun getCategory(): EmiRecipeCategory {
        return category
    }

    override fun getId(): Identifier? {
        return recipe.id
    }

    override fun getInputs(): MutableList<EmiIngredient> {
        return input
    }

    override fun getOutputs(): MutableList<EmiStack> {
        return output
    }

    override fun getDisplayWidth(): Int {
        return xSize
    }

    override fun getDisplayHeight(): Int {
        return ySize
    }
}
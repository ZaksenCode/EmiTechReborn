package me.zaksen.emitechreborn.client.emi.atr

import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiCraftingRecipe
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.EmiPlugin
import me.zaksen.emitechreborn.client.emi.SubPlugin
import me.zaksen.emitechreborn.client.emi.atr.machine.CanningMachineRecipe
import me.zaksen.emitechreborn.client.emi.tr.TechRebornPlugin
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeManager
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.Identifier
import net.pitan76.advancedreborn.AdvancedReborn
import net.pitan76.advancedreborn.Blocks
import net.pitan76.advancedreborn.Recipes
import reborncore.common.crafting.RebornRecipe
import techreborn.TechReborn

@Suppress("UNCHECKED_CAST")
class AdvancedTechRebornPlugin(
    private val techRebornPlugin: TechRebornPlugin
): SubPlugin {
    override fun load(plugin: EmiPlugin) {

    }

    override fun registry(plugin: EmiPlugin, registry: EmiRegistry, manager: RecipeManager) {
        val canningCategory = plugin.madeEntry(
            EmiStack.of(Blocks.CANNING_MACHINE.get()),
            Identifier.of(AdvancedReborn.MOD_ID, "canning_machine"),
            Recipes.CANNING_MACHINE as RecipeType<Recipe<RecipeInput>>
        ) { _registry, category, recipe ->
            _registry.addRecipe(CanningMachineRecipe(category, recipe as RecipeEntry<RebornRecipe>))
        }

        registry.addCategory(canningCategory.first)
        registry.addWorkstation(canningCategory.first, canningCategory.second.workstations.first())

        manager.listAllOfType(canningCategory.second.recipeType).forEach { entry ->
            canningCategory.second.registerFunc.accept(registry, canningCategory.first, entry)
        }

        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(Blocks.INDUCTION_FURNACE.get()))
        registry.addWorkstation(
            techRebornPlugin.getCategory(Identifier.of(TechReborn.MOD_ID, "grinder"))!!,
            EmiStack.of(Blocks.ROTARY_GRINDER.get())
        )
        registry.addWorkstation(
            techRebornPlugin.getCategory(Identifier.of(TechReborn.MOD_ID, "extractor"))!!,
            EmiStack.of(Blocks.CENTRIFUGAL_EXTRACTOR.get())
        )
        registry.addWorkstation(
            techRebornPlugin.getCategory(Identifier.of(TechReborn.MOD_ID, "compressor"))!!,
            EmiStack.of(Blocks.SINGULARITY_COMPRESSOR.get())
        )
    }
}
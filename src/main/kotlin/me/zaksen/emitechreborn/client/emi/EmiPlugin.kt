package me.zaksen.emitechreborn.client.emi

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.tr.TechRebornPlugin
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.Identifier
import reborncore.common.misc.TriConsumer
import techreborn.TechReborn

class EmiPlugin: EmiPlugin {

    private val subPlugins: Map<String, SubPlugin> = mapOf(
        Pair("techreborn", TechRebornPlugin(this))
    )

    init {
        subPlugins.forEach {
            it.value.load(this)
        }
    }

    override fun register(registry: EmiRegistry) {
        val manager = registry.recipeManager

        subPlugins.forEach {
            it.value.registry(this, registry, manager)
        }
    }

    fun madeEntry(
        stack: EmiStack,
        id: String,
        type: RecipeType<out Recipe<RecipeInput>>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
    ): Pair<EmiRecipeCategory, CategoryEntry> {
        val category = EmiRecipeCategory(Identifier.of(TechReborn.MOD_ID, id), stack)
        return Pair(category, CategoryEntry(setOf(stack), type as RecipeType<Recipe<RecipeInput>>, registerFunc))
    }

    fun madeEntry(
        stacks: Set<EmiStack>,
        id: String,
        type: RecipeType<out Recipe<RecipeInput>>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
    ): Pair<EmiRecipeCategory, CategoryEntry> {
        val category = EmiRecipeCategory(Identifier.of(TechReborn.MOD_ID, id), stacks.first())
        return  Pair(category, CategoryEntry(stacks, type as RecipeType<Recipe<RecipeInput>>, registerFunc))
    }
}

data class CategoryEntry(
    val workstations: Set<EmiStack>,
    val recipeType: RecipeType<Recipe<RecipeInput>>,
    val registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
)
package me.zaksen.emitechreborn.client.emi

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.atr.AdvancedTechRebornPlugin
import me.zaksen.emitechreborn.client.emi.tr.TechRebornPlugin
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.Identifier
import reborncore.common.misc.TriConsumer

@Suppress("UNCHECKED_CAST")
class EmiPlugin: EmiPlugin {

    private val subPlugins: Map<String, SubPlugin> by lazy {
        val techRebornPlugin = TechRebornPlugin(this)

        mapOf(
            Pair("techreborn", techRebornPlugin),
            Pair("advanced_reborn", AdvancedTechRebornPlugin(techRebornPlugin))
        )
    }

    init {
        subPlugins.forEach {
            if(FabricLoader.getInstance().isModLoaded(it.key)) {
                it.value.load(this)
            }
        }
    }

    override fun register(registry: EmiRegistry) {
        val manager = registry.recipeManager

        subPlugins.forEach {
            if(FabricLoader.getInstance().isModLoaded(it.key)) {
                it.value.registry(this, registry, manager)
            }
        }
    }

    fun madeEntry(
        stack: EmiStack,
        id: Identifier,
        type: RecipeType<out Recipe<RecipeInput>>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
    ): Pair<EmiRecipeCategory, CategoryEntry> {
        val category = EmiRecipeCategory(id, stack)
        return Pair(category, CategoryEntry(setOf(stack), type as RecipeType<Recipe<RecipeInput>>, registerFunc))
    }

    fun madeEntry(
        stacks: Set<EmiStack>,
        id: Identifier,
        type: RecipeType<out Recipe<RecipeInput>>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
    ): Pair<EmiRecipeCategory, CategoryEntry> {
        val category = EmiRecipeCategory(id, stacks.first())
        return  Pair(category, CategoryEntry(stacks, type as RecipeType<Recipe<RecipeInput>>, registerFunc))
    }
}

data class CategoryEntry(
    val workstations: Set<EmiStack>,
    val recipeType: RecipeType<Recipe<RecipeInput>>,
    val registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
)
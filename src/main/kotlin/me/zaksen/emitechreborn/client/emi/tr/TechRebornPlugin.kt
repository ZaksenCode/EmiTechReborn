package me.zaksen.emitechreborn.client.emi.tr

import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.CategoryEntry
import me.zaksen.emitechreborn.client.emi.EmiPlugin
import me.zaksen.emitechreborn.client.emi.SubPlugin
import me.zaksen.emitechreborn.client.emi.tr.machine.*
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeManager
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.input.RecipeInput
import net.minecraft.util.Identifier
import reborncore.common.crafting.RebornRecipe
import reborncore.common.misc.TriConsumer
import techreborn.TechReborn
import techreborn.init.ModRecipes
import techreborn.init.TRContent.Machine

@Suppress("UNCHECKED_CAST")
class TechRebornPlugin(private val plugin: EmiPlugin): SubPlugin {

    private val categoryEntries: MutableMap<Identifier, EmiRecipeCategory> = mutableMapOf()
    private val categories: MutableMap<EmiRecipeCategory, CategoryEntry> = mutableMapOf()

    override fun load(plugin: EmiPlugin) {
        addCategory(Machine.ASSEMBLY_MACHINE, ModRecipes.ASSEMBLING_MACHINE) { registry, category, recipe ->
            registry.addRecipe(AssemblingMachineRecipe(category, recipe))
        }
        addCategory(Machine.ALLOY_SMELTER, setOf(EmiStack.of(Machine.IRON_ALLOY_FURNACE.stack)), ModRecipes.ALLOY_SMELTER) { registry, category, recipe ->
            registry.addRecipe(TwoInputsCenterOutputRecipe(category, recipe))
        }
        addCategory(Machine.CHEMICAL_REACTOR, ModRecipes.CHEMICAL_REACTOR) { registry, category, recipe ->
            registry.addRecipe(TwoInputsCenterOutputRecipe(category, recipe))
        }
        addCategory(Machine.COMPRESSOR, ModRecipes.COMPRESSOR) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.EXTRACTOR, ModRecipes.EXTRACTOR) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.GRINDER, ModRecipes.GRINDER) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.DISTILLATION_TOWER, ModRecipes.DISTILLATION_TOWER) { registry, category, recipe ->
            registry.addRecipe(DistillationTowerRecipe(category, recipe))
        }
        addCategory(Machine.INDUSTRIAL_BLAST_FURNACE, ModRecipes.BLAST_FURNACE) { registry, category, recipe ->
            registry.addRecipe(BlastFurnaceRecipe(category, recipe))
        }
        addCategory(Machine.INDUSTRIAL_ELECTROLYZER, ModRecipes.INDUSTRIAL_ELECTROLYZER) { registry, category, recipe ->
            registry.addRecipe(ElectrolyzerRecipe(category, recipe))
        }
        addCategory(Machine.FLUID_REPLICATOR, ModRecipes.FLUID_REPLICATOR) { registry, category, recipe ->
            registry.addRecipe(FluidReplicatorRecipe(category, recipe))
        }
        addCategory(Machine.IMPLOSION_COMPRESSOR, ModRecipes.IMPLOSION_COMPRESSOR) { registry, category, recipe ->
            registry.addRecipe(ImplosionCompressorRecipe(category, recipe))
        }
        addCategory(Machine.SOLID_CANNING_MACHINE, ModRecipes.SOLID_CANNING_MACHINE) { registry, category, recipe ->
            registry.addRecipe(TwoInputsCenterOutputRecipe(category, recipe))
        }
        addCategory(Machine.VACUUM_FREEZER, ModRecipes.VACUUM_FREEZER) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.WIRE_MILL, ModRecipes.WIRE_MILL) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.SCRAPBOXINATOR, ModRecipes.SCRAPBOX) { registry, category, recipe ->
            registry.addRecipe(OneInputOneOutputRecipe(category, recipe))
        }
        addCategory(Machine.FUSION_CONTROL_COMPUTER, ModRecipes.FUSION_REACTOR) { registry, category, recipe ->
            registry.addRecipe(TwoInputsCenterOutputRecipe(category, recipe))
        }
        addCategory(Machine.INDUSTRIAL_CENTRIFUGE, ModRecipes.CENTRIFUGE) { registry, category, recipe ->
            registry.addRecipe(IndustrialCentrifugeRecipe(category, recipe))
        }
        addCategory(Machine.INDUSTRIAL_SAWMILL, ModRecipes.INDUSTRIAL_SAWMILL) { registry, category, recipe ->
            registry.addRecipe(SawmillMachineRecipe(category, recipe))
        }
        addCategory(Machine.INDUSTRIAL_GRINDER, ModRecipes.INDUSTRIAL_GRINDER) { registry, category, recipe ->
            registry.addRecipe(GrinderRecipe(category, recipe))
        }
        addCategory(Machine.ROLLING_MACHINE, ModRecipes.ROLLING_MACHINE) { registry, category, recipe ->
            registry.addRecipe(RollingMachineRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.RollingMachineRecipe>))
        }

        addCategory(Machine.THERMAL_GENERATOR, ModRecipes.THERMAL_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.FluidGeneratorRecipe>))
        }
        addCategory(Machine.GAS_TURBINE, ModRecipes.GAS_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.FluidGeneratorRecipe>))
        }
        addCategory(Machine.DIESEL_GENERATOR, ModRecipes.DIESEL_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.FluidGeneratorRecipe>))
        }
        addCategory(Machine.SEMI_FLUID_GENERATOR, ModRecipes.SEMI_FLUID_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.FluidGeneratorRecipe>))
        }
        addCategory(Machine.PLASMA_GENERATOR, ModRecipes.PLASMA_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<techreborn.recipe.recipes.FluidGeneratorRecipe>))
        }
    }

    fun getCategory(id: Identifier): EmiRecipeCategory? {
        return categoryEntries[id]
    }

    private fun addCategory(
        machine: Machine,
        type: RecipeType<out RebornRecipe>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<RebornRecipe>>
    ) {
        val entry = plugin.madeEntry(
            EmiStack.of(machine.stack),
            Identifier.of(TechReborn.MOD_ID, machine.name),
            type as RecipeType<Recipe<RecipeInput>>,
            registerFunc as TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
        )
        categoryEntries[entry.first.id] = entry.first
        categories[entry.first] = entry.second
    }

    private fun addCategory(
        machine: Machine,
        additionalStacks: Set<EmiStack>,
        type: RecipeType<out RebornRecipe>,
        registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<RebornRecipe>>
    ) {
        val entry = plugin.madeEntry(
            setOf(EmiStack.of(machine.stack)).plus(additionalStacks),
            Identifier.of(TechReborn.MOD_ID, machine.name),
            type as RecipeType<Recipe<RecipeInput>>,
            registerFunc as TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<Recipe<RecipeInput>>>
        )
        categoryEntries[entry.first.id] = entry.first
        categories[entry.first] = entry.second
    }


    override fun registry(plugin: EmiPlugin, registry: EmiRegistry, manager: RecipeManager) {
        categories.forEach {
            registry.addCategory(it.key)
            it.value.workstations.forEach { workstation ->
                registry.addWorkstation(it.key, workstation)
            }

            manager.listAllOfType(it.value.recipeType).forEach { entry ->
                it.value.registerFunc.accept(registry, it.key, entry)
            }
        }

        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(Machine.IRON_FURNACE.stack))
        registry.addWorkstation(VanillaEmiRecipeCategories.SMELTING, EmiStack.of(Machine.ELECTRIC_FURNACE.stack))
    }
}
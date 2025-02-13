package me.zaksen.emitechreborn.client.emi

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.machine.*
import net.minecraft.recipe.RecipeEntry
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import reborncore.common.crafting.RebornRecipe
import reborncore.common.misc.TriConsumer
import techreborn.TechReborn
import techreborn.init.ModRecipes
import techreborn.init.TRContent.Machine
import techreborn.recipe.recipes.FluidGeneratorRecipe

@Suppress("UNCHECKED_CAST")
class EmiPlugin: EmiPlugin {

    private val categories: MutableMap<EmiRecipeCategory, CategoryEntry> = mutableMapOf()

    init {
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
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<FluidGeneratorRecipe>))
        }
        addCategory(Machine.GAS_TURBINE, ModRecipes.GAS_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<FluidGeneratorRecipe>))
        }
        addCategory(Machine.DIESEL_GENERATOR, ModRecipes.DIESEL_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<FluidGeneratorRecipe>))
        }
        addCategory(Machine.SEMI_FLUID_GENERATOR, ModRecipes.SEMI_FLUID_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<FluidGeneratorRecipe>))
        }
        addCategory(Machine.PLASMA_GENERATOR, ModRecipes.PLASMA_GENERATOR) { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe as RecipeEntry<FluidGeneratorRecipe>))
        }
    }

    private fun addCategory(machine: Machine, type: RecipeType<out RebornRecipe>, registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<RebornRecipe>>) {
        val machineStack = EmiStack.of(machine.stack)
        val category = EmiRecipeCategory(
            Identifier.of(TechReborn.MOD_ID, machine.name),
            machineStack
        )
        categories[category] = CategoryEntry(setOf(machineStack), type as RecipeType<RebornRecipe>, registerFunc)
    }

    private fun addCategory(machine: Machine, additionalStations: Set<EmiStack>, type: RecipeType<out RebornRecipe>, registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<RebornRecipe>>) {
        val machineStack = EmiStack.of(machine.stack)
        val category = EmiRecipeCategory(
            Identifier.of(TechReborn.MOD_ID, machine.name),
            machineStack
        )
        categories[category] = CategoryEntry(setOf(machineStack).plus(additionalStations), type as RecipeType<RebornRecipe>, registerFunc)
    }

    override fun register(registry: EmiRegistry) {
        val manager = registry.recipeManager

        categories.forEach {
            registry.addCategory(it.key)
            it.value.workstations.forEach { workstation ->
                registry.addWorkstation(it.key, workstation)
            }

            manager.listAllOfType(it.value.recipeType).forEach { entry ->
                it.value.registerFunc.accept(registry, it.key, entry)
            }
        }
    }
}

private data class CategoryEntry(
    val workstations: Set<EmiStack>,
    val recipeType: RecipeType<RebornRecipe>,
    val registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RecipeEntry<RebornRecipe>>
)
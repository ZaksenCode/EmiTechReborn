package me.zaksen.emitechreborn.client.emi

import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry
import dev.emi.emi.api.recipe.EmiRecipeCategory
import dev.emi.emi.api.stack.EmiStack
import me.zaksen.emitechreborn.client.emi.machine.*
import net.minecraft.util.Identifier
import reborncore.common.crafting.RebornRecipe
import reborncore.common.crafting.RebornRecipeType
import reborncore.common.misc.TriConsumer
import techreborn.TechReborn
import techreborn.api.generator.EFluidGenerator
import techreborn.api.generator.FluidGeneratorRecipeList
import techreborn.api.generator.GeneratorRecipeHelper
import techreborn.api.recipe.recipes.IndustrialGrinderRecipe
import techreborn.init.ModRecipes
import techreborn.init.TRContent.Machine

class EmiPlugin: EmiPlugin {

    private val categories: MutableMap<EmiRecipeCategory, CategoryEntry> = mutableMapOf()
    private val fluidCategories: MutableMap<EmiRecipeCategory, FluidCategoryEntry> = mutableMapOf()

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
            registry.addRecipe(RollingMachineRecipe(category, recipe as techreborn.api.recipe.recipes.RollingMachineRecipe))
        }

        addFluidCategory(Machine.THERMAL_GENERATOR, GeneratorRecipeHelper.getFluidRecipesForGenerator(EFluidGenerator.THERMAL))
        { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe, Identifier.of(TechReborn.MOD_ID, "thermal_generator/" + recipe.fluid.bucketItem.translationKey)!!.withPrefixedPath("/")))
        }
        addFluidCategory(Machine.GAS_TURBINE, GeneratorRecipeHelper.getFluidRecipesForGenerator(EFluidGenerator.GAS))
        { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe, Identifier.of(TechReborn.MOD_ID, "gas_turbine/" + recipe.fluid.bucketItem.translationKey)!!.withPrefixedPath("/")))
        }
        addFluidCategory(Machine.DIESEL_GENERATOR, GeneratorRecipeHelper.getFluidRecipesForGenerator(EFluidGenerator.DIESEL))
        { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe, Identifier.of(TechReborn.MOD_ID, "diesel_generator/" + recipe.fluid.bucketItem.translationKey)!!.withPrefixedPath("/")))
        }
        addFluidCategory(Machine.SEMI_FLUID_GENERATOR, GeneratorRecipeHelper.getFluidRecipesForGenerator(EFluidGenerator.SEMIFLUID))
        { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe, Identifier.of(TechReborn.MOD_ID, "semi_fluid_generator/" + recipe.fluid.bucketItem.translationKey)!!.withPrefixedPath("/")))
        }
        addFluidCategory(Machine.PLASMA_GENERATOR, GeneratorRecipeHelper.getFluidRecipesForGenerator(EFluidGenerator.PLASMA))
        { registry, category, recipe ->
            registry.addRecipe(FluidGeneratorRecipe(category, recipe, Identifier.of(TechReborn.MOD_ID, "plasma_generator/" + recipe.fluid.bucketItem.translationKey)!!.withPrefixedPath("/")))
        }
    }

    private fun addCategory(machine: Machine, type: RebornRecipeType<*>, registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RebornRecipe>) {
        val machineStack = EmiStack.of(machine.stack)
        val category = EmiRecipeCategory(
            Identifier.of(TechReborn.MOD_ID, machine.name),
            machineStack
        )
        categories[category] = CategoryEntry(setOf(machineStack), type, registerFunc)
    }

    private fun addCategory(machine: Machine, additionalStations: Set<EmiStack>, type: RebornRecipeType<*>, registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RebornRecipe>) {
        val machineStack = EmiStack.of(machine.stack)
        val category = EmiRecipeCategory(
            Identifier.of(TechReborn.MOD_ID, machine.name),
            machineStack
        )
        categories[category] = CategoryEntry(setOf(machineStack).plus(additionalStations), type, registerFunc)
    }

    private fun addFluidCategory(machine: Machine, fluidRecipes: FluidGeneratorRecipeList, registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, techreborn.api.generator.FluidGeneratorRecipe>) {
        val stack = EmiStack.of(machine.stack)
        val category = EmiRecipeCategory(
            Identifier.of(TechReborn.MOD_ID, machine.name),
            stack
        )
        fluidCategories[category] = FluidCategoryEntry(setOf(stack), fluidRecipes, registerFunc)
    }

    override fun register(registry: EmiRegistry) {
        val manager = registry.recipeManager

        categories.forEach {
            registry.addCategory(it.key)
            it.value.workstations.forEach { workstation ->
                registry.addWorkstation(it.key, workstation)
            }

            manager.listAllOfType(it.value.recipeType).forEach { recipe ->
                it.value.registerFunc.accept(registry, it.key, recipe)
            }
        }

        fluidCategories.forEach {
            registry.addCategory(it.key)
            it.value.workstations.forEach { workstation ->
                registry.addWorkstation(it.key, workstation)
            }

            it.value.recipes.recipes.forEach { recipe ->
                it.value.registerFunc.accept(registry, it.key, recipe)
            }
        }
    }
}

private data class CategoryEntry(
    val workstations: Set<EmiStack>,
    val recipeType: RebornRecipeType<*>,
    val registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, RebornRecipe>
)

private data class FluidCategoryEntry(
    val workstations: Set<EmiStack>,
    val recipes: FluidGeneratorRecipeList,
    val registerFunc: TriConsumer<EmiRegistry, EmiRecipeCategory, techreborn.api.generator.FluidGeneratorRecipe>
)
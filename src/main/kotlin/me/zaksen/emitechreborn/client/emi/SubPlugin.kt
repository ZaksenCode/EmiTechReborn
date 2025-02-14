package me.zaksen.emitechreborn.client.emi

import dev.emi.emi.api.EmiRegistry
import net.minecraft.recipe.RecipeManager

interface SubPlugin {
    fun load(plugin: EmiPlugin)
    fun registry(plugin: EmiPlugin, registry: EmiRegistry, manager: RecipeManager)
}
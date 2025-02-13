package me.zaksen.emitechreborn.client.emi.widget

import dev.emi.emi.EmiRenderHelper
import dev.emi.emi.api.widget.Bounds
import dev.emi.emi.api.widget.Widget
import dev.emi.emi.config.EmiConfig
import dev.emi.emi.runtime.EmiDrawContext
import me.zaksen.emitechreborn.client.EmitechrebornClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import reborncore.client.gui.GuiSprites
import reborncore.client.gui.GuiSprites.drawSprite

open class EnergyDisplayWidget(
    private val x: Int,
    private val y: Int,
    private val cost: Int,
    private val total: Int
): Widget() {
    private val width = 14
    private val height = 49

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        drawBackground(context, mouseX, mouseY, delta)
        drawEnergy(context, mouseX, mouseY, delta)
        drawOverlay(context, mouseX, mouseY, delta)
    }

    private fun drawEnergy(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        drawSprite(context, GuiSprites.POWER_BAR_OVERLAY, bounds.x, bounds.y)
    }

    private fun drawBackground(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        drawSprite(context, GuiSprites.POWER_BAR_BASE, bounds.x - 1, bounds.y - 1)
    }

    private fun drawOverlay(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if (this.shouldDrawHighlight(mouseX, mouseY)) {
            this.drawHighlight(context)
        }
    }

    private fun shouldDrawHighlight(mouseX: Int, mouseY: Int): Boolean {
        return bounds.contains(mouseX, mouseY) && EmiConfig.showHoverOverlay
    }

    private fun drawHighlight(context: DrawContext) {
        EmiRenderHelper.drawSlotHightlight(EmiDrawContext.wrap(context), bounds.x, bounds.y, bounds.width - 1, bounds.height - 1, 200)
    }

    override fun getBounds(): Bounds {
        return Bounds(x, y, width, height)
    }

    override fun getTooltip(mouseX: Int, mouseY: Int): MutableList<TooltipComponent> {
        val result = mutableListOf<TooltipComponent>()

        result.add(TooltipComponent.of(Text.translatable("techreborn.jei.recipe.energy").asOrderedText()))
        result.add(TooltipComponent.of(Text.translatable("techreborn.jei.recipe.running.cost", "E", cost).formatted(Formatting.GRAY).asOrderedText()))
        result.add(TooltipComponent.of(Text.translatable("techreborn.jei.recipe.generator.total", total).formatted(Formatting.GRAY).asOrderedText()))
        result.add(TooltipComponent.of(Text.of("").asOrderedText()))
        result.add(TooltipComponent.of(EmitechrebornClient.getFormattedModName("techreborn").asOrderedText()))

        return result
    }
}
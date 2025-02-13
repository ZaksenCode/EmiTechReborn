package me.zaksen.emitechreborn.client.emi.widget

import dev.emi.emi.api.widget.Bounds
import dev.emi.emi.api.widget.Widget
import net.minecraft.client.gui.DrawContext
import reborncore.client.gui.GuiBuilder
import reborncore.client.gui.GuiSprites.drawSprite

open class ProgressBarWidget(
    private val x: Int,
    private val y: Int,
    private val animationDuration: Double,
    private val direction: GuiBuilder.ProgressDirection
): Widget() {

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        drawBackground(context, mouseX, mouseX, delta)
        drawOverlay(context, mouseX, mouseY, delta)
    }

    private fun drawBackground(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        drawSprite(context, direction.baseSprite, bounds.x, bounds.y)
    }

    private fun drawOverlay(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        var j = ((System.currentTimeMillis() / animationDuration) % 1.0 * 16.0).toInt()
        if(j < 0) { j = 0 }

        when(direction) {
            GuiBuilder.ProgressDirection.RIGHT -> {
                context.drawTexture(
                    GuiBuilder.GUI_ELEMENTS,
                    bounds.x, bounds.y,
                    direction.xActive.toFloat(), direction.yActive.toFloat(),
                    j, 10,
                    256, 256
                )
            }
            GuiBuilder.ProgressDirection.LEFT -> {
                context.drawTexture(
                    GuiBuilder.GUI_ELEMENTS,
                    bounds.x + 16 - j, bounds.y,
                    direction.xActive.toFloat() + 16 - j, direction.yActive.toFloat(),
                    j, 10,
                    256, 256
                )
            }
            GuiBuilder.ProgressDirection.DOWN -> context.drawTexture(
                GuiBuilder.GUI_ELEMENTS,
                bounds.x, bounds.y + 16 - j,
                direction.xActive.toFloat(), direction.yActive.toFloat() + 16 - j,
                10, j,
                256, 256
            )
            GuiBuilder.ProgressDirection.UP -> context.drawTexture(
                GuiBuilder.GUI_ELEMENTS,
                bounds.x, bounds.y,
                direction.xActive.toFloat(), direction.yActive.toFloat(),
                10, j,
                256, 256
            )
        }
    }

    override fun getBounds(): Bounds {
        return Bounds(x, y, direction.xActive, direction.yActive)
    }
}
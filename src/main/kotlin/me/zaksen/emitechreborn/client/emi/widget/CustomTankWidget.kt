package me.zaksen.emitechreborn.client.emi.widget

import dev.emi.emi.EmiRenderHelper
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.api.stack.FluidEmiStack
import dev.emi.emi.api.widget.Bounds
import dev.emi.emi.api.widget.TankWidget
import dev.emi.emi.platform.EmiAgnos
import dev.emi.emi.runtime.EmiDrawContext
import net.minecraft.client.gui.DrawContext
import net.minecraft.fluid.Fluid
import reborncore.client.gui.GuiSprites
import kotlin.math.max
import kotlin.math.min

class CustomTankWidget(
    stack: EmiStack,
    x: Int, y: Int,
    private val _capacity: Long
) : TankWidget(
    stack,
    x, y,
    22, 56,
    _capacity
) {
    override fun drawBackground(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if (this.drawBack) {
            GuiSprites.drawSprite(
                context,
                GuiSprites.TANK_BACKGROUND,
                x,
                y
            )
        }
    }

    override fun drawStack(draw: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        val ingredient = this.getStack()

        for (stack in ingredient.emiStacks) {
            val fes = stack.key
            if (fes is Fluid) {
                val fes = FluidEmiStack(fes, stack.componentChanges, ingredient.amount)
                val floaty = EmiAgnos.isFloatyFluid(fes)
                val bounds = this.getBounds()
                val x = bounds.x() + 4
                val y = bounds.y() + 4
                val w = bounds.width() - 8
                val h = bounds.height() - 8
                val filledHeight = max(
                    1.0,
                    min(h.toLong().toDouble(), (fes.amount * h.toLong() / _capacity).toDouble()).toInt().toDouble()
                ).toInt()
                val sy = if (floaty) y else y + h

                var oy = 0
                while (oy < filledHeight) {
                    val rh = min(16.0, (filledHeight - oy).toDouble()).toInt()

                    var ox = 0
                    while (ox < w) {
                        val rw = min(16.0, (w - ox).toDouble()).toInt()
                        if (floaty) {
                            EmiAgnos.renderFluid(fes, draw!!.matrices, x + ox, sy + oy, delta, 0, 0, rw, rh)
                        } else {
                            EmiAgnos.renderFluid(
                                fes,
                                draw!!.matrices,
                                x + ox,
                                sy + (oy + rh) * -1,
                                delta,
                                0,
                                16 - rh,
                                rw,
                                rh
                            )
                        }
                        ox += 16
                    }
                    oy += 16
                }

                return
            }
        }
    }

    override fun drawSlotHighlight(draw: DrawContext, bounds: Bounds) {
        EmiRenderHelper.drawSlotHightlight(
            EmiDrawContext.wrap(draw),
            bounds.x() + 4,
            bounds.y() + 4,
            bounds.width() - 8,
            bounds.height() - 8,
            200
        )
    }
}
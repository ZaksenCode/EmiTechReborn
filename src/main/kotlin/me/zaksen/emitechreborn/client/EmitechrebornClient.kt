package me.zaksen.emitechreborn.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.minecraft.text.MutableText
import net.minecraft.text.Text
import net.minecraft.util.Formatting

class EmitechrebornClient : ClientModInitializer {
    override fun onInitializeClient() {

    }

    companion object {
        private val modContainers: MutableMap<String, ModContainer> = mutableMapOf()

        fun getFormattedModName(id: String): Text {
            val name = getModName(id)
            return MutableText.of(name.content).formatted(Formatting.BLUE, Formatting.ITALIC)
        }

        fun getModName(id: String): Text {
            if(modContainers.containsKey(id)) {
                val modContainer = modContainers[id]!!
                return Text.literal(modContainer.metadata.name)
            }
            val modContainer = FabricLoader.getInstance().getModContainer(id)

            if(modContainer.isEmpty) {
                return Text.empty()
            }

            val container = modContainer.get()
            return Text.literal(container.metadata.name)
        }
    }
}

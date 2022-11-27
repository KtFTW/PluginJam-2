package net.stckoverflw.pluginjam.task.tasks

import net.axay.kspigot.runnables.sync
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.stckoverflw.pluginjam.game.gamePlayers
import net.stckoverflw.pluginjam.task.Task
import net.stckoverflw.pluginjam.util.text
import org.bukkit.Material
import org.bukkit.block.BlockFace

class ForceBlockTask(private val block: Material)
    : Task(text("Stehe auf ").append(Component.translatable(block.translationKey()))) {

    companion object {
        val BLOCKS = listOf(Material.DEEPSLATE, Material.POINTED_DRIPSTONE)
    }
    override fun register() {
        addTask(task(sync = false, delay = 0, 10) {
            val player = gamePlayers.firstOrNull { it.location.block.getRelative(BlockFace.DOWN).type == block}
            sync { if (player != null) finish(player) }
        }!!)
    }
}

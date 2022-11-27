package net.stckoverflw.pluginjam.task.tasks

import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.stckoverflw.pluginjam.game.gamePlayers
import net.stckoverflw.pluginjam.task.Task
import net.stckoverflw.pluginjam.util.text
import org.bukkit.Material
import org.bukkit.block.BlockFace

class ForceBlockTask(private val block: Material)
    : Task(text("Stehe auf ").append(Component.translatable(block.translationKey()))) {
    override fun register() {
        addTask(task(sync = true, delay = 0, 10) {
            val player = gamePlayers.firstOrNull { it.location.block.getRelative(BlockFace.DOWN).type == block}
            if (player != null) finish(player)
        }!!)
    }
}

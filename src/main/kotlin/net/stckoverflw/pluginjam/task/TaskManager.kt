package net.stckoverflw.pluginjam.task

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.format.NamedTextColor
import net.stckoverflw.pluginjam.task.tasks.FindItemTask
import net.stckoverflw.pluginjam.task.tasks.ForceBlockTask
import net.stckoverflw.pluginjam.task.tasks.ReachHeightTask
import org.bukkit.entity.Player
import kotlin.random.Random
import kotlin.random.nextInt

object TaskManager {
    private val tasks = mutableListOf<Task>()
    operator fun invoke() {
        tasks.add(ReachHeightTask(Random.nextInt(140..250)))
        tasks.add(FindItemTask(FindItemTask.ITEMS.random()))
        tasks.add(ForceBlockTask(ForceBlockTask.BLOCKS.random()))
    }

    fun makeComponent(player: Player): Component {
        return Component.join(JoinConfiguration.separator(Component.text(" / ").color(NamedTextColor.GREEN)),
            tasks.map { it.makeComponent(player) }.toSet())
    }
}


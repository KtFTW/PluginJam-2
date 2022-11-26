package net.stckoverflw.pluginjam.task

import net.stckoverflw.pluginjam.task.tasks.FindItemTask
import net.stckoverflw.pluginjam.task.tasks.ReachHeightTask
import org.bukkit.Material
import kotlin.random.Random
import kotlin.random.nextInt

object TaskManager {
    val tasks = mutableListOf<Task>()

    operator fun invoke() {
        val totalTasks = mutableListOf<Task>()
        tasks.add(ReachHeightTask(Random.nextInt(180..320)))
        tasks.add(FindItemTask(Material.AIR))
    }
}
